# Java NIO 学习示例

本目录包含 Java NIO（New I/O）的完整学习示例，适合面试准备和深入理解。

## 核心概念

### 1. NIO vs BIO（传统 I/O）

| 特性 | BIO（阻塞 I/O） | NIO（非阻塞 I/O） |
|------|----------------|------------------|
| 阻塞性 | 阻塞 | 非阻塞 |
| 线程模型 | 一个连接一个线程 | 一个线程处理多个连接 |
| 性能 | 适合连接数少的场景 | 适合高并发场景 |
| 复杂度 | 简单 | 相对复杂 |

### 2. NIO 三大核心组件

#### Channel（通道）
- **作用**：类似于流，但可以双向传输数据
- **类型**：
  - `ServerSocketChannel`：服务端监听通道
  - `SocketChannel`：客户端/服务端数据传输通道
  - `FileChannel`：文件通道
  - `DatagramChannel`：UDP 通道

#### Buffer（缓冲区）
- **作用**：数据容器，Channel 读写数据必须通过 Buffer
- **核心属性**：
  - `capacity`：容量，创建后不可变
  - `position`：当前位置
  - `limit`：可读写的最大位置
- **核心方法**：
  - `flip()`：切换到读模式
  - `clear()`：清空 Buffer，准备重新写入
  - `compact()`：保留未读数据，准备继续写入

#### Selector（选择器）
- **作用**：监听多个 Channel 的事件
- **事件类型**：
  - `OP_ACCEPT`：接受连接
  - `OP_CONNECT`：连接就绪
  - `OP_READ`：数据可读
  - `OP_WRITE`：数据可写

## 文件说明

### 1. `NioServer.java` - NIO 服务端
- 完整的 NIO 服务端实现
- 演示 Selector 的使用
- 非阻塞事件处理
- 支持多客户端连接

**运行方式：**
```bash
./gradlew :my-jdk-debug:quickRun -PmainClass=mine.jdk.debug.nio.NioServer
```

### 2. `NioClient.java` - NIO 客户端
- 完整的 NIO 客户端实现
- 演示 SocketChannel 的使用
- 非阻塞连接和读写

**运行方式：**
```bash
./gradlew :my-jdk-debug:quickRun -PmainClass=mine.jdk.debug.nio.NioClient
```

### 3. `NioDemo.java` - 完整演示
- 同时启动服务端和客户端
- 自动发送测试消息
- 适合快速理解 NIO 工作流程

**运行方式：**
```bash
./gradlew :my-jdk-debug:quickRun -PmainClass=mine.jdk.debug.nio.NioDemo
```

### 4. `BufferDemo.java` - Buffer 详解
- Buffer 核心概念演示
- 写操作、读操作
- `flip()`, `clear()`, `compact()` 方法详解
- `mark()` 和 `reset()` 方法演示

**运行方式：**
```bash
./gradlew :my-jdk-debug:quickRun -PmainClass=mine.jdk.debug.nio.BufferDemo
```

## 面试重点

### 1. NIO 的核心优势
- **非阻塞**：一个线程可以处理多个连接
- **事件驱动**：基于 Selector 的事件通知机制
- **高效**：适合高并发场景

### 2. Buffer 的工作流程
```
写入数据 → position 增加 → flip() → 切换到读模式 → 读取数据 → clear()/compact() → 准备下次写入
```

### 3. Selector 的工作流程
```
1. 创建 Selector
2. 将 Channel 注册到 Selector
3. 调用 select() 阻塞等待事件
4. 获取就绪的 SelectionKey
5. 处理事件
6. 移除已处理的 Key
7. 循环步骤 3-6
```

### 4. 常见问题

**Q: NIO 和 BIO 的区别？**
- BIO 是阻塞的，一个连接一个线程
- NIO 是非阻塞的，一个线程可以处理多个连接

**Q: Buffer 的 flip() 方法做了什么？**
- 将 limit 设置为当前 position
- 将 position 设置为 0
- 切换到读模式

**Q: Selector 的作用？**
- 监听多个 Channel 的事件
- 一个线程可以处理多个连接
- 提高并发性能

**Q: NIO 适合什么场景？**
- 高并发场景
- 连接数多但每个连接数据量不大的场景
- 需要非阻塞 I/O 的场景

## 学习建议

1. **先理解 Buffer**：运行 `BufferDemo.java`，理解 Buffer 的工作原理
2. **再看服务端**：运行 `NioServer.java`，理解 Selector 的使用
3. **然后看客户端**：运行 `NioClient.java`，理解 SocketChannel 的使用
4. **最后看完整示例**：运行 `NioDemo.java`，理解完整的通信流程

## 扩展学习

- **AIO（异步 I/O）**：Java 7+ 引入的异步非阻塞 I/O
- **Netty**：基于 NIO 的高性能网络框架
- **Reactor 模式**：NIO 的设计模式
