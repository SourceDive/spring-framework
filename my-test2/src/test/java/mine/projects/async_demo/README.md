# Spring Framework @Async 调试演示程序

这是一个用于调试Spring Framework @Async功能的最简单测试程序。

## 文件说明

- `AsyncConfig.java` - 异步配置类，启用@EnableAsync并配置线程池
- `AsyncService.java` - 异步服务类，包含各种@Async方法
- `AsyncTest.java` - JUnit测试类，用于测试异步功能（使用纯Spring Framework）
- `AsyncDemoApplication.java` - 主应用程序，作为调试入口

## 运行方式

### 1. 运行主应用程序（推荐用于调试）
```bash
# 在项目根目录下执行
./gradlew :my-test2:test --tests mine.projects.async_demo.AsyncDemoApplication
```

或者直接运行：
```bash
cd my-test2
./gradlew test --tests mine.projects.async_demo.AsyncDemoApplication
```

### 2. 运行JUnit测试
```bash
# 运行所有异步测试
./gradlew :my-test2:test --tests mine.projects.async_demo.AsyncTest

# 运行特定测试方法
./gradlew :my-test2:test --tests mine.projects.async_demo.AsyncTest.testAsyncMethod
```

## 调试要点

1. **线程名称观察**: 注意观察主线程和异步线程的名称变化
2. **执行顺序**: 异步方法调用后立即返回，主线程继续执行
3. **并发执行**: 多个异步方法可以并发执行
4. **线程池配置**: 在AsyncConfig中配置了自定义线程池

## 预期输出示例

```
=== Spring @Async 调试演示程序 ===
主线程: main

=== 演示1: 基本异步方法调用 ===
异步方法调用后立即返回，主线程继续执行
异步方法开始执行，线程: Async-1
异步处理完成: Hello Async (线程: Async-1)
异步方法执行结果: 异步处理完成: Hello Async (线程: Async-1)
```

## 调试技巧

1. 在IDE中设置断点，观察异步方法的执行流程
2. 使用Thread.currentThread().getName()观察线程切换
3. 通过日志输出观察异步方法的执行时机
4. 测试异常情况下的异步方法行为
