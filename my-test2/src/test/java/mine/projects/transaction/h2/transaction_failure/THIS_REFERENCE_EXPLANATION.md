# 内部方法调用事务失效原理详解

## 核心问题：this 指向目标对象而非代理对象

### 1. Spring AOP 代理机制

Spring 使用代理模式来实现 AOP 功能，包括事务管理：

```java
// Spring 容器中实际存储的是代理对象
UserService userService = context.getBean(UserService.class);

// 代理对象的结构
class UserServiceProxy implements UserService {
    private UserServiceImpl target; // 目标对象
    
    @Override
    public void callTransactionalMethodInternally(String username) {
        // 1. 检查方法是否有@Transactional注解
        // 2. 如果有，创建事务
        // 3. 调用目标对象的方法
        target.callTransactionalMethodInternally(username);
        // 4. 提交或回滚事务
    }
    
    @Override
    public void createUserWithException(String username) {
        // 1. 检查方法是否有@Transactional注解
        // 2. 如果有，创建事务
        // 3. 调用目标对象的方法
        target.createUserWithException(username);
        // 4. 提交或回滚事务
    }
}
```

### 2. 内部调用的问题

当你在类内部使用 `this` 调用方法时：

```java
public class UserServiceImpl implements UserService {
    
    public void callTransactionalMethodInternally(String username) {
        // this 指向的是 UserServiceImpl 实例（目标对象）
        // 不是 UserServiceProxy 实例（代理对象）
        this.createUserWithException(username + "_internal");
    }
    
    @Transactional
    public void createUserWithException(String username) {
        // 这个方法上的@Transactional注解会被忽略
        // 因为调用没有经过代理对象
        jdbcTemplate.update(INSERT_SQL, username);
        throw new RuntimeException("异常");
    }
}
```

### 3. 调用链对比

#### 外部调用（有效）
```
外部代码
    ↓
代理对象.callTransactionalMethodInternally()
    ↓
代理对象检查@Transactional注解
    ↓
创建事务
    ↓
目标对象.callTransactionalMethodInternally()
    ↓
目标对象.createUserWithException()
    ↓
提交/回滚事务
```

#### 内部调用（失效）
```
外部代码
    ↓
代理对象.callTransactionalMethodInternally()
    ↓
代理对象检查@Transactional注解
    ↓
创建事务
    ↓
目标对象.callTransactionalMethodInternally()
    ↓
this.createUserWithException() ← 直接调用目标对象方法
    ↓
@Transactional注解被忽略
    ↓
没有事务保护
    ↓
提交/回滚事务（只对callTransactionalMethodInternally有效）
```

### 4. 验证 this 引用

运行 `testVerifyThisReference()` 方法，观察日志输出：

```
===> this.getClass().getName(): mine.projects.transaction.h2.transaction_failure.service.UserServiceImpl
===> this.getClass().getSimpleName(): UserServiceImpl
===> 是否是代理对象: false
===> createUserWithException方法是否有@Transactional注解: true
```

**关键观察**：
- `this.getClass().getName()` 显示的是 `UserServiceImpl`，不是代理类
- `是否是代理对象: false` 确认 this 指向目标对象
- 方法确实有 `@Transactional` 注解，但由于是内部调用，注解被忽略

### 5. 解决方案

#### 方案1：使用 AopContext.currentProxy()
```java
@EnableAspectJAutoProxy(exposeProxy = true)
public class TransactionFailureConfig {
    // 配置
}

// 在实现类中使用
public void callTransactionalMethodInternally(String username) {
    UserService proxy = (UserService) AopContext.currentProxy();
    proxy.createUserWithException(username + "_internal");
}
```

#### 方案2：使用 @Autowired 自注入
```java
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserService self; // 自注入，获得代理对象
    
    public void callTransactionalMethodInternally(String username) {
        self.createUserWithException(username + "_internal");
    }
}
```

#### 方案3：提取到另一个服务类
```java
@Service
public class UserService {
    @Autowired
    private UserTransactionService userTransactionService;
    
    public void callTransactionalMethodInternally(String username) {
        userTransactionService.createUserWithException(username + "_internal");
    }
}

@Service
public class UserTransactionService {
    @Transactional
    public void createUserWithException(String username) {
        // 事务方法
    }
}
```

### 6. 总结

内部方法调用事务失效的根本原因是：

1. **`this` 指向目标对象**：不是 Spring 创建的代理对象
2. **绕过了 AOP 代理**：直接调用目标对象方法
3. **注解被忽略**：`@Transactional` 注解不会被处理
4. **没有事务保护**：方法执行没有事务上下文

这是 Spring AOP 的一个限制，也是为什么推荐将事务方法提取到独立的服务类中的原因。

### 7. 调试建议

1. **运行 `testVerifyThisReference()`**：观察 this 引用的类型
2. **设置断点**：在 `callTransactionalMethodInternally` 和 `createUserWithException` 方法中设置断点
3. **观察调用栈**：查看方法调用的层次结构
4. **检查日志**：观察事务开始/结束的日志输出
5. **验证数据**：确认数据是否插入（事务失效时仍会插入）
