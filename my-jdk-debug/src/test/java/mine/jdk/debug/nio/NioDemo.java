package mine.jdk.debug.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zero
 * @description Java NIO 完整演示示例
 * 
 * 这个类会同时启动服务端和客户端，用于演示完整的 NIO 通信流程
 * 适合快速测试和理解 NIO 的工作原理
 * 
 * @date 2025-12-04
 */
public class NioDemo {
    
    private static final int PORT = 8888;
    private static final int BUFFER_SIZE = 1024;
    
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // 启动服务端
        executor.submit(() -> {
            try {
                startServer();
            } catch (Exception e) {
                System.err.println("服务端异常: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // 等待服务端启动
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 启动客户端
        executor.submit(() -> {
            try {
                startClient();
            } catch (Exception e) {
                System.err.println("客户端异常: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        // 等待任务完成
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * 启动服务端
     */
    private static void startServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        System.out.println("=== NIO 服务端启动，监听端口: " + PORT + " ===");
        
        int messageCount = 0;
        long startTime = System.currentTimeMillis();
        
        // 只处理 5 条消息后退出（用于演示）
        while (messageCount < 5) {
            int readyChannels = selector.select(1000); // 1秒超时
            
            if (readyChannels == 0) {
                continue;
            }
            
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    System.out.println("[服务端] 新客户端连接: " + client.getRemoteAddress());
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    
                    // 发送欢迎消息
                    String welcomeMsg = "欢迎连接到 NIO 服务器！\n";
                    ByteBuffer buffer = ByteBuffer.wrap(welcomeMsg.getBytes(StandardCharsets.UTF_8));
                    client.write(buffer);
                    
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    
                    int bytesRead = client.read(buffer);
                    if (bytesRead > 0) {
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String message = new String(bytes, StandardCharsets.UTF_8).trim();
                        
                        System.out.println("[服务端] 收到消息 #" + (++messageCount) + ": " + message);
                        
                        // 回显消息
                        String response = "服务器收到: " + message + "\n";
                        ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
                        client.write(responseBuffer);
                        
                    } else if (bytesRead < 0) {
                        System.out.println("[服务端] 客户端断开连接");
                        client.close();
                    }
                }
                
                keyIterator.remove();
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("=== 服务端处理完成，耗时: " + (endTime - startTime) + "ms ===");
        selector.close();
        serverChannel.close();
    }
    
    /**
     * 启动客户端
     */
    private static void startClient() throws IOException, InterruptedException {
        Thread.sleep(500); // 等待服务端完全启动
        
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", PORT));
        
        // 等待连接完成
        while (!socketChannel.finishConnect()) {
            Thread.sleep(100);
        }
        
        System.out.println("=== NIO 客户端已连接 ===");
        
        // 读取欢迎消息
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int bytesRead = socketChannel.read(buffer);
        if (bytesRead > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            System.out.print("[客户端] " + new String(bytes, StandardCharsets.UTF_8));
        }
        
        // 发送 5 条测试消息
        String[] messages = {
            "Hello NIO!",
            "这是第二条消息",
            "NIO 是非阻塞的",
            "一个线程可以处理多个连接",
            "最后一条消息"
        };
        
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i];
            System.out.println("[客户端] 发送消息: " + message);
            
            ByteBuffer sendBuffer = ByteBuffer.wrap((message + "\n").getBytes(StandardCharsets.UTF_8));
            socketChannel.write(sendBuffer);
            
            // 读取服务器响应
            Thread.sleep(200);
            buffer.clear();
            bytesRead = socketChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                byte[] responseBytes = new byte[buffer.remaining()];
                buffer.get(responseBytes);
                System.out.print("[客户端] " + new String(responseBytes, StandardCharsets.UTF_8));
            }
            
            Thread.sleep(300);
        }
        
        System.out.println("=== 客户端完成 ===");
        socketChannel.close();
    }
}
