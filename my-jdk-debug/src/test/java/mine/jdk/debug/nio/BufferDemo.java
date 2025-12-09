package mine.jdk.debug.nio;

import java.nio.ByteBuffer;

/**
 * @author zero
 * @description Buffer（缓冲区）核心概念演示
 * 
 * Buffer 是 NIO 中非常重要的概念，理解 Buffer 的工作原理是掌握 NIO 的关键
 * 
 * Buffer 的三个核心属性：
 * 1. capacity（容量）：Buffer 的最大容量，创建后不能改变
 * 2. position（位置）：当前读写位置
 * 3. limit（限制）：可以读写的最大位置
 * 
 * Buffer 的两种模式：
 * 1. 写模式：position 指向下一个要写入的位置，limit = capacity
 * 2. 读模式：position 指向下一个要读取的位置，limit 指向最后一个可读位置
 * 
 * @date 2025-12-04
 */
public class BufferDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Buffer 基础操作演示 ===\n");
        
        // 1. 创建 Buffer
        demonstrateBufferCreation();
        
        // 2. 写操作
        demonstrateWrite();
        
        // 3. 读操作
        demonstrateRead();
        
        // 4. flip() 方法
        demonstrateFlip();
        
        // 5. clear() 和 compact() 方法
        demonstrateClearAndCompact();
        
        // 6. mark() 和 reset() 方法
        demonstrateMarkAndReset();
    }
    
    /**
     * 演示 Buffer 的创建
     */
    private static void demonstrateBufferCreation() {
        System.out.println("1. Buffer 创建");
        System.out.println("-------------------");
        
        // 方式1：分配指定大小的 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println("创建容量为 10 的 Buffer:");
        printBufferState(buffer);
        
        // 方式2：包装现有数组
        byte[] array = new byte[10];
        ByteBuffer wrappedBuffer = ByteBuffer.wrap(array);
        System.out.println("\n包装现有数组:");
        printBufferState(wrappedBuffer);
        
        System.out.println();
    }
    
    /**
     * 演示写操作
     */
    private static void demonstrateWrite() {
        System.out.println("2. Buffer 写操作");
        System.out.println("-------------------");
        
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println("初始状态（写模式）:");
        printBufferState(buffer);
        
        // 写入数据
        buffer.put((byte) 'H');
        buffer.put((byte) 'e');
        buffer.put((byte) 'l');
        buffer.put((byte) 'l');
        buffer.put((byte) 'o');
        
        System.out.println("\n写入 5 个字节后:");
        printBufferState(buffer);
        System.out.println();
    }
    
    /**
     * 演示读操作
     */
    private static void demonstrateRead() {
        System.out.println("3. Buffer 读操作");
        System.out.println("-------------------");
        
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("Hello".getBytes());
        
        System.out.println("写入数据后（写模式）:");
        printBufferState(buffer);
        
        // 切换到读模式
        buffer.flip();
        System.out.println("\n调用 flip() 后（读模式）:");
        printBufferState(buffer);
        
        // 读取数据
        System.out.println("\n读取数据:");
        while (buffer.hasRemaining()) {
            byte b = buffer.get();
            System.out.println("  读取: " + (char) b + " (position: " + buffer.position() + ")");
        }
        
        System.out.println("\n读取完成后:");
        printBufferState(buffer);
        System.out.println();
    }
    
    /**
     * 演示 flip() 方法
     */
    private static void demonstrateFlip() {
        System.out.println("4. flip() 方法详解");
        System.out.println("-------------------");
        
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("Hello".getBytes());
        
        System.out.println("写入后（写模式）:");
        printBufferState(buffer);
        System.out.println("  position = 5, limit = 10, capacity = 10");
        
        buffer.flip();
        System.out.println("\n调用 flip() 后（读模式）:");
        printBufferState(buffer);
        System.out.println("  position = 0, limit = 5, capacity = 10");
        System.out.println("  flip() 做了两件事：");
        System.out.println("    1. 将 limit 设置为当前 position");
        System.out.println("    2. 将 position 设置为 0");
        System.out.println();
    }
    
    /**
     * 演示 clear() 和 compact() 方法
     */
    private static void demonstrateClearAndCompact() {
        System.out.println("5. clear() 和 compact() 方法");
        System.out.println("-------------------");
        
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("Hello".getBytes());
        buffer.flip();
        
        // 读取部分数据
        buffer.get(); // 'H'
        buffer.get(); // 'e'
        
        System.out.println("读取 2 个字节后:");
        printBufferState(buffer);
        
        // clear() - 清空 Buffer，准备重新写入
        buffer.clear();
        System.out.println("\n调用 clear() 后:");
        printBufferState(buffer);
        System.out.println("  clear() 将 position=0, limit=capacity，但数据还在！");
        
        // 重新写入
        buffer.put("World".getBytes());
        buffer.flip();
        System.out.println("\n重新写入 'World' 后:");
        printBufferState(buffer);
        
        // compact() - 保留未读数据，准备继续写入
        buffer = ByteBuffer.allocate(10);
        buffer.put("Hello".getBytes());
        buffer.flip();
        buffer.get(); // 'H'
        buffer.get(); // 'e'
        
        System.out.println("\n再次读取 2 个字节后:");
        printBufferState(buffer);
        
        buffer.compact();
        System.out.println("\n调用 compact() 后:");
        printBufferState(buffer);
        System.out.println("  compact() 将未读数据移到开头，position 指向下一个写入位置");
        System.out.println("  未读数据 'llo' 被保留在 Buffer 开头");
        System.out.println();
    }
    
    /**
     * 演示 mark() 和 reset() 方法
     */
    private static void demonstrateMarkAndReset() {
        System.out.println("6. mark() 和 reset() 方法");
        System.out.println("-------------------");
        
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("Hello".getBytes());
        buffer.flip();
        
        System.out.println("初始状态:");
        printBufferState(buffer);
        
        buffer.get(); // 'H'
        buffer.get(); // 'e'
        
        System.out.println("\n读取 2 个字节后:");
        printBufferState(buffer);
        
        buffer.mark(); // 标记当前位置
        System.out.println("\n调用 mark() 标记当前位置");
        
        buffer.get(); // 'l'
        buffer.get(); // 'l'
        buffer.get(); // 'o'
        
        System.out.println("\n继续读取 3 个字节后:");
        printBufferState(buffer);
        
        buffer.reset(); // 重置到标记位置
        System.out.println("\n调用 reset() 后，回到标记位置:");
        printBufferState(buffer);
        System.out.println();
    }
    
    /**
     * 打印 Buffer 的状态
     */
    private static void printBufferState(ByteBuffer buffer) {
        System.out.println("  capacity: " + buffer.capacity());
        System.out.println("  position: " + buffer.position());
        System.out.println("  limit:    " + buffer.limit());
        System.out.println("  remaining: " + buffer.remaining());
    }
}
