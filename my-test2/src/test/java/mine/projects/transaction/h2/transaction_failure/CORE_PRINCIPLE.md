# Spring 事务失效核心原理

## 核心判断：事务注解修饰的方法的调用者决定事务是否生效

### 1. 基本原理

**事务是否生效 = 调用者是否是代理对象**

```java
// ✅ 代理对象调用 → 事务生效
proxy.createUserWithException("user1");

// ❌ 目标对象调用 → 事务失效  
this.createUserWithException("user1");
```

### 2. 调用链分析

#### 代理对象调用（事务生效）
```
外部代码
    ↓
代理对象.createUserWithException()  ← 关键：调用者是代理对象
    ↓
Spring AOP 拦截
    ↓
检查 @Transactional 注解
    ↓
创建事务
    ↓
调用目标对象方法
    ↓
执行业务逻辑
    ↓
提交/回滚事务
```

#### 目标对象调用（事务失效）
```
外部代码
    ↓
代理对象.callTransactionalMethodInternally()
    ↓
目标对象.callTransactionalMethodInternally()
    ↓
this.createUserWithException()  ← 关键：调用者是目标对象（this）
    ↓
直接执行业务逻辑（绕过AOP）
    ↓
@Transactional 注解被忽略
    ↓
无事务保护
```

### 3. 关键区别

| 调用方式 | 调用者 | AOP拦截 | 注解处理 | 事务保护 | 结果 |
|----------|--------|---------|----------|----------|------|
| 外部调用 | 代理对象 | ✅ | ✅ | ✅ | 事务生效 |
| 内部调用 | 目标对象 | ❌ | ❌ | ❌ | 事务失效 |

### 4. 验证方法

运行 `testCompareProxyVsTargetCall()` 测试：

```java
// 1. 代理对象调用 - 异常会回滚
userService.createUserWithException("user1"); // 数据不会插入

// 2. 目标对象调用 - 异常不会回滚  
this.createUserWithException("user2"); // 数据会插入
```

### 5. 实际应用

#### 问题场景
```java
@Service
public class UserService {
    
    @Transactional
    public void createUser(String username) {
        // 业务逻辑
    }
    
    public void batchCreateUsers(List<String> usernames) {
        for (String username : usernames) {
            this.createUser(username); // ❌ 事务失效
        }
    }
}
```

#### 解决方案
```java
@Service
public class UserService {
    
    @Autowired
    private UserService self; // 自注入，获得代理对象
    
    @Transactional
    public void createUser(String username) {
        // 业务逻辑
    }
    
    public void batchCreateUsers(List<String> usernames) {
        for (String username : usernames) {
            self.createUser(username); // ✅ 事务生效
        }
    }
}
```

### 6. 总结

**核心原理**：事务注解修饰的方法的上一级调用者决定事务是否生效

- **代理对象调用** → Spring AOP 拦截 → 检查注解 → 创建事务 → **事务生效**
- **目标对象调用** → 直接执行 → 忽略注解 → 无事务保护 → **事务失效**

这个原理不仅适用于事务，也适用于所有 Spring AOP 功能（如缓存、安全等）。

### 7. 记忆口诀

**"看调用者，不看被调用者"**

- 看 `@Transactional` 方法的**调用者**是谁
- 如果是**代理对象** → 事务生效
- 如果是**目标对象** → 事务失效
