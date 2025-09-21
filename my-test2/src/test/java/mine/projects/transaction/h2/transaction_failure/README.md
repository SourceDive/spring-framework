# 事务失效场景调试指南

这个包包含了各种Spring事务失效场景的演示代码，可以作为调试入口来学习事务机制。

## 文件结构

- `config/TransactionFailureConfig.java` - Spring配置类
- `service/UserService.java` - 服务接口
- `service/UserServiceImpl.java` - 服务实现类，包含各种事务失效场景
- `TransactionFailureTest.java` - JUnit测试类
- `TransactionFailureDebugApplication.java` - 调试入口程序

## 事务失效场景列表

### 1. 方法访问级别问题
- **私有方法**: `callPrivateMethod()` - 调用真正的私有方法，@Transactional失效
- **包级别方法**: `callPackageMethod()` - 调用包级别访问方法，@Transactional失效
- **私有方法异常**: `callPrivateMethodWithException()` - 私有方法中抛出异常不会回滚
- **静态方法**: `createUserStaticMethod()` - 静态方法无法被代理
- **final方法**: `createUserFinalMethod()` - final方法无法被代理

### 2. 内部调用问题
- **同类内部调用**: `callTransactionalMethodInternally()` - 同一个类内部调用事务方法
- **无注解调用**: `callTransactionalMethodWithoutAnnotation()` - 没有@Transactional注解的方法调用事务方法

### 3. 异常处理问题
- **捕获异常**: `createUserWithCaughtException()` - 事务方法中捕获异常
- **检查异常**: `createUserWithCheckedException()` - 非RuntimeException异常（默认只回滚RuntimeException）

### 4. 事务传播级别问题
- **NOT_SUPPORTED**: `createUserWithNotSupported()` - 不支持事务
- **NEVER**: `createUserWithNever()` - 从不使用事务
- **SUPPORTS**: `createUserWithSupports()` - 支持事务（当前没有事务时失效）
- **MANDATORY**: `createUserWithMandatory()` - 强制要求事务（当前没有事务时抛出异常）
- **REQUIRES_NEW**: `createUserWithRequiresNew()` - 创建新事务
- **NESTED**: `createUserWithNested()` - 创建嵌套事务

## 调试方法

### 方法1: 使用调试应用程序
运行 `TransactionFailureDebugApplication.main()` 方法，在相应的方法中设置断点：

```java
// 在IDE中设置断点
userService.createUserSuccess("debug_user1");           // 断点1
userService.createUserWithException("debug_user2");     // 断点2
userService.callTransactionalMethodInternally("debug_user3"); // 断点3
// ... 其他方法
```

### 方法2: 使用JUnit测试
运行 `TransactionFailureTest` 中的各个测试方法，观察事务行为：

```java
@Test
void testCreateUserSuccess() {
    // 设置断点观察正常事务
    userService.createUserSuccess("user1");
}

@Test
void testCreateUserWithException() {
    // 设置断点观察异常回滚
    userService.createUserWithException("user2");
}
```

## 调试技巧

1. **观察日志输出**: 每个方法都有详细的日志输出，显示事务的开始和结束
2. **检查数据库状态**: 使用 `getAllUserNames()` 方法查看最终的用户列表
3. **设置断点**: 在关键方法中设置断点，观察Spring事务代理的行为
4. **查看事务状态**: 在调试器中查看当前线程的事务状态

## 预期结果

- **正常事务**: 数据成功插入
- **异常回滚**: 数据不会插入（RuntimeException）
- **事务失效**: 数据会插入但不在事务中
- **传播异常**: 某些传播级别会抛出异常

## 注意事项

1. 每次运行都会重新创建数据库表
2. 使用H2内存数据库，重启后数据会丢失
3. 日志会显示详细的事务执行过程
4. 可以通过修改传播级别和异常处理来观察不同的行为
