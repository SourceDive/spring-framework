# Spring代理对象内部结构详解

## 问题：目标对象存储在哪里？

既然Spring容器中存放的是代理对象，那么目标对象存储在哪里呢？

## 答案：目标对象存储在代理对象内部

### 1. 代理对象的内部结构

Spring创建的代理对象内部会持有目标对象的引用：

```java
// Spring实际创建的代理对象（简化版）
class UserServiceProxy implements UserService {
    private UserServiceImpl target; // 目标对象存储在这里
    
    public UserServiceProxy(UserServiceImpl target) {
        this.target = target;
    }
    
    @Override
    public void createUserWithException(String username) {
        // 1. 检查@Transactional注解
        // 2. 创建事务
        // 3. 调用目标对象方法
        target.createUserWithException(username);
        // 4. 提交/回滚事务
    }
}
```

### 2. 两种代理方式的不同存储

#### JDK动态代理
```java
// JDK动态代理使用InvocationHandler
class UserServiceInvocationHandler implements InvocationHandler {
    private UserServiceImpl target; // 目标对象存储在InvocationHandler中
    
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 调用目标对象方法
        return method.invoke(target, args);
    }
}
```

#### CGLIB代理
```java
// CGLIB代理继承目标类
class UserServiceProxy extends UserServiceImpl {
    private UserServiceImpl target; // 目标对象存储在代理类中
    
    @Override
    public void createUserWithException(String username) {
        // 调用目标对象方法
        target.createUserWithException(username);
    }
}
```

### 3. 验证目标对象存储

运行 `testVerifyTargetObjectStorage()` 测试，观察日志输出：

```
===> 当前对象类型: mine.projects.transaction.h2.transaction_failure.service.UserServiceImpl$$EnhancerBySpringCGLIB$$xxx
===> 当前对象是否为代理: true
===> 通过反射获取的目标对象: mine.projects.transaction.h2.transaction_failure.service.UserServiceImpl@xxx
===> 目标对象类型: mine.projects.transaction.h2.transaction_failure.service.UserServiceImpl
===> 目标对象是否为代理: false
```

### 4. 代理对象的生命周期

```
1. Spring容器启动
    ↓
2. 创建目标对象 (UserServiceImpl)
    ↓
3. 创建代理对象 (UserServiceProxy)
    ↓
4. 将目标对象注入到代理对象中
    ↓
5. 将代理对象注册到容器中
    ↓
6. 外部调用代理对象方法
    ↓
7. 代理对象调用目标对象方法
```

### 5. 内存结构图

```
Spring容器
    ↓
┌─────────────────────────────────────┐
│ UserServiceProxy (代理对象)          │
│ ┌─────────────────────────────────┐ │
│ │ private UserServiceImpl target  │ │ ← 目标对象存储在这里
│ └─────────────────────────────────┘ │
│                                     │
│ createUserWithException() {         │
│   // AOP逻辑                        │
│   target.createUserWithException()  │ ← 调用目标对象
│   // AOP逻辑                        │
│ }                                   │
└─────────────────────────────────────┘
```

### 6. 关键理解

1. **容器中只有代理对象**：Spring容器中注册的是代理对象，不是目标对象
2. **目标对象被代理对象持有**：目标对象作为代理对象的字段存在
3. **外部调用代理对象**：所有外部调用都通过代理对象
4. **内部调用目标对象**：`this` 指向的是目标对象，不是代理对象

### 7. 为什么这样设计？

1. **透明性**：外部调用者不需要知道代理的存在
2. **性能**：避免重复创建目标对象
3. **灵活性**：可以在代理对象中添加额外的逻辑
4. **内存效率**：目标对象只创建一次，被代理对象持有

### 8. 调试建议

运行 `testVerifyTargetObjectStorage()` 方法，观察：

1. **代理对象类型**：通常包含 `$$EnhancerBySpringCGLIB$$` 或 `$Proxy`
2. **目标对象字段**：通过反射查看 `target` 字段
3. **字段结构**：了解代理对象的内部字段
4. **父类关系**：CGLIB代理会继承目标类

### 9. 总结

**目标对象存储在代理对象内部**：

- Spring容器中只有代理对象
- 目标对象作为代理对象的字段存在
- 外部调用通过代理对象
- 内部调用直接访问目标对象
- 这就是为什么内部调用会绕过AOP的原因
