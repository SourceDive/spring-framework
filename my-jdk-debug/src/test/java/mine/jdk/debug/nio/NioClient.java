package mine.jdk.debug.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author zero
 * @description Java NIO 客户端示例
 * 
 * 演示如何使用 SocketChannel 连接到 NIO 服务器
 * 
 * @date 2025-12-04
 */
public class NioClient {
    
    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    private static final int BUFFER_SIZE = 1024;
    
    public static void main(String[] args) {
        NioClient client = new NioClient();
        try {
            client.start();
        } catch (IOException e) {
            System.err.println("客户端连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 启动客户端
     */
    public void start() throws IOException {
        // 1. 创建 SocketChannel（客户端通道）
        SocketChannel socketChannel = SocketChannel.open();
        
        // 2. 设置为非阻塞模式
        socketChannel.configureBlocking(false);
        
        // 3. 连接到服务器（非阻塞连接）
        socketChannel.connect(new InetSocketAddress(HOST, PORT));
        
        // 4. 等待连接完成
        while (!socketChannel.finishConnect()) {
            System.out.println("正在连接服务器...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("已连接到服务器: " + socketChannel.getRemoteAddress());
        
        // 5. 读取服务器欢迎消息
        readServerMessage(socketChannel);
        
        // 6. 启动发送消息线程
        startMessageSender(socketChannel);
        
        // 7. 持续监听服务器消息
        listenServerMessages(socketChannel);
    }
    
    /**
     * 读取服务器消息
     */
    private void readServerMessage(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int bytesRead = socketChannel.read(buffer);
        
        if (bytesRead > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String message = new String(bytes, StandardCharsets.UTF_8);
            System.out.print("服务器: " + message);
        }
    }
    
    /**
     * 启动消息发送线程
     */
    private void startMessageSender(SocketChannel socketChannel) {
        Thread senderThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入消息（输入 'quit' 退出）:");
            
            while (true) {
                try {
                    String input = scanner.nextLine();
                    
                    if ("quit".equalsIgnoreCase(input)) {
                        socketChannel.close();
                        System.out.println("客户端已断开连接");
                        System.exit(0);
                        break;
                    }
                    
                    // 发送消息到服务器
                    ByteBuffer buffer = ByteBuffer.wrap((input + "\n").getBytes(StandardCharsets.UTF_8));
                    socketChannel.write(buffer);
                    
                } catch (IOException e) {
                    System.err.println("发送消息失败: " + e.getMessage());
                    break;
                }
            }
            scanner.close();
        });
        
        senderThread.setDaemon(true);
        senderThread.start();
    }
    
    /**
     * 监听服务器消息
     */
    private void listenServerMessages(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        
        while (socketChannel.isOpen()) {
            try {
                int bytesRead = socketChannel.read(buffer);
                
                if (bytesRead > 0) {
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String message = new String(bytes, StandardCharsets.UTF_8);
                    System.out.print("服务器: " + message);
                    buffer.clear();
                } else if (bytesRead < 0) {
                    System.out.println("服务器关闭了连接");
                    break;
                }
                
                // 避免 CPU 占用过高
                Thread.sleep(100);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        socketChannel.close();
    }
}
