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

/**
 * @author zero
 * @description Java NIO 服务端示例 - 演示非阻塞 I/O
 * 
 * 核心概念：
 * 1. Channel（通道）：类似于流，但可以双向传输数据
 * 2. Buffer（缓冲区）：数据容器，用于 Channel 读写
 * 3. Selector（选择器）：用于监听多个 Channel 的事件（连接、读、写）
 * 
 * NIO 的优势：
 * - 非阻塞：一个线程可以处理多个连接
 * - 事件驱动：基于 Selector 的事件通知机制
 * - 高效：适合高并发场景
 * 
 * @date 2025-12-04
 */
public class NioServer {
    
    private static final int PORT = 8888;
    private static final int BUFFER_SIZE = 1024;
    
    public static void main(String[] args) {
        NioServer server = new NioServer();
        try {
            server.start();
        } catch (IOException e) {
            System.err.println("服务器启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 启动 NIO 服务器
     */
    public void start() throws IOException {
        // 1. 创建 Selector（选择器）- 用于监听多个 Channel 的事件
        Selector selector = Selector.open();
        
        // 2. 创建 ServerSocketChannel（服务端通道）
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        
        // 3. 设置为非阻塞模式（这是 NIO 的关键！）
        serverChannel.configureBlocking(false);
        
        // 4. 绑定端口
        serverChannel.socket().bind(new InetSocketAddress(PORT));
        
        // 5. 将 ServerSocketChannel 注册到 Selector，监听 ACCEPT 事件（新连接）
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        System.out.println("NIO 服务器启动，监听端口: " + PORT);
        System.out.println("等待客户端连接...");
        
        // 6. 事件循环 - 这是 NIO 的核心！
        while (true) {
            // 阻塞等待，直到有事件发生（或超时）
            // 返回就绪的 Channel 数量
            int readyChannels = selector.select();
            
            if (readyChannels == 0) {
                continue;
            }
            
            // 7. 获取所有就绪的事件
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                
                // 8. 处理不同类型的事件
                if (key.isAcceptable()) {
                    // 有新连接请求
                    handleAccept(selector, key);
                } else if (key.isReadable()) {
                    // 有数据可读
                    handleRead(key);
                }
                
                // 9. 重要：处理完后移除该事件，避免重复处理
                keyIterator.remove();
            }
        }
    }
    
    /**
     * 处理新连接
     */
    private void handleAccept(Selector selector, SelectionKey key) throws IOException {
        // 获取 ServerSocketChannel
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        
        // 接受连接，获取客户端 SocketChannel
        SocketChannel clientChannel = serverChannel.accept();
        
        System.out.println("新客户端连接: " + clientChannel.getRemoteAddress());
        
        // 设置为非阻塞模式
        clientChannel.configureBlocking(false);
        
        // 将客户端 Channel 注册到 Selector，监听 READ 事件（数据可读）
        clientChannel.register(selector, SelectionKey.OP_READ);
        
        // 发送欢迎消息
        String welcomeMsg = "欢迎连接到 NIO 服务器！\n";
        ByteBuffer buffer = ByteBuffer.wrap(welcomeMsg.getBytes(StandardCharsets.UTF_8));
        clientChannel.write(buffer);
    }
    
    /**
     * 处理读取数据
     */
    private void handleRead(SelectionKey key) throws IOException {
        // 获取客户端 SocketChannel
        SocketChannel clientChannel = (SocketChannel) key.channel();
        
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        
        try {
            // 读取数据到缓冲区
            int bytesRead = clientChannel.read(buffer);
            
            if (bytesRead > 0) {
                // 切换缓冲区为读模式
                buffer.flip();
                
                // 将字节转换为字符串
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                String message = new String(bytes, StandardCharsets.UTF_8);
                
                System.out.println("收到客户端消息: " + message.trim());
                
                // 回显消息给客户端
                String response = "服务器收到: " + message.trim() + "\n";
                ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
                clientChannel.write(responseBuffer);
                
            } else if (bytesRead < 0) {
                // 客户端关闭连接
                System.out.println("客户端断开连接: " + clientChannel.getRemoteAddress());
                clientChannel.close();
            }
            
        } catch (IOException e) {
            System.out.println("读取数据异常，关闭连接: " + clientChannel.getRemoteAddress());
            clientChannel.close();
        }
    }
}
