# my-jdk-debug 模块使用说明

## 概述

`my-jdk-debug` 是一个专门用于学习JDK核心类的独立模块，它不依赖Spring框架，可以快速编译和运行。

## 优势

1. **快速编译**：只编译JDK相关代码，不需要编译整个Spring框架
2. **独立运行**：可以快速运行和测试JDK核心功能
3. **保持关联**：仍然在Spring源码项目中，便于管理

## 使用方法

### 1. 运行ConcurrentHashMap演示
```bash
./gradlew :my-jdk-debug:quickRun
```

### 2. 运行ConcurrentHashMap总结
```bash
./gradlew :my-jdk-debug:quickRun -PmainClass=mine.jdk.debug.ConcurrentHashMapSummary
```

### 3. 运行自定义主类
```bash
./gradlew :my-jdk-debug:quickRun -PmainClass=你的主类名
```

### 4. 只编译不运行
```bash
./gradlew :my-jdk-debug:quickCompile
```

## 项目结构

```
my-jdk-debug/
├── build.gradle                    # 构建配置
└── src/
    └── test/
        └── java/
            └── mine/
                └── jdk/
                    └── debug/
                        ├── ConcurrentHashMapDemo.java      # 基础演示
                        └── ConcurrentHashMapSummary.java   # 特性总结
```

## 与my-test2的区别

| 特性 | my-test2 | my-jdk-debug |
|------|----------|--------------|
| 依赖 | Spring框架 | 仅JDK标准库 |
| 编译速度 | 慢（需要编译Spring） | 快（只编译JDK代码） |
| 适用场景 | Spring相关测试 | JDK核心类学习 |
| 运行方式 | `./gradlew :my-test2:test` | `./gradlew :my-jdk-debug:quickRun` |

## 扩展使用

你可以在这个模块中添加其他JDK核心类的学习代码，比如：
- `HashMap` vs `ConcurrentHashMap`
- `ArrayList` vs `Vector`
- `ThreadLocal` 使用
- `AtomicInteger` 等原子类
- `CountDownLatch`、`CyclicBarrier` 等同步工具

## 注意事项

1. 这个模块只适合纯JDK相关的学习代码
2. 如果需要Spring相关功能，请使用`my-test2`模块
3. 所有主类都应该放在`mine.jdk.debug`包下
4. 使用`quickRun`任务可以快速运行，无需等待Spring编译
