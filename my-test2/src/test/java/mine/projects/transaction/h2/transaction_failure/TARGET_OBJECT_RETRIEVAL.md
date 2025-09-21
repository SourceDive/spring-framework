# 如何从代理对象获取目标对象

## 核心问题

**如果想要获取目标对象，需要通过代理对象来获取吗？**

**答案：是的！** 目标对象存储在代理对象内部，必须通过代理对象来获取。

## 获取目标对象的方法

### 1. 通过反射获取（通用方法）

```java
public static Object getTargetObject(Object proxy) {
    try {
        // 对于CGLIB代理
        Field targetField = proxy.getClass().getDeclaredField("target");
        targetField.setAccessible(true);
        return targetField.get(proxy);
    } catch (NoSuchFieldException e) {
        try {
            // 对于JDK动态代理
            Field hField = proxy.getClass().getSuperclass().getDeclaredField("h");
            hField.setAccessible(true);
            Object handler = hField.get(proxy);
            
            Field targetField = handler.getClass().getDeclaredField("target");
            targetField.setAccessible(true);
            return targetField.get(handler);
        } catch (Exception ex) {
            return null;
        }
    } catch (Exception e) {
        return null;
    }
}
```

### 2. 使用Spring工具类（推荐）

```java
import org.springframework.aop.framework.AopUtils;

// 获取目标对象
Object target = AopUtils.getTargetObject(proxy);

// 检查是否是代理对象
boolean isProxy = AopUtils.isAopProxy(proxy);

// 获取目标类
Class<?> targetClass = AopUtils.getTargetClass(proxy);
```

### 3. 实际使用示例

```java
@Service
public class UserService {
    
    @Autowired
    private UserService self; // 自注入，获得代理对象
    
    public void someMethod() {
        // 获取目标对象
        Object target = AopUtils.getTargetObject(self);
        
        // 通过目标对象调用方法（事务失效）
        if (target instanceof UserService) {
            UserService targetService = (UserService) target;
            targetService.transactionalMethod(); // 事务失效
        }
    }
    
    @Transactional
    public void transactionalMethod() {
        // 事务方法
    }
}
```

## 代理对象内部结构

### CGLIB代理
```java
class UserServiceProxy extends UserServiceImpl {
    private UserServiceImpl target; // 目标对象存储在这里
    
    @Override
    public void transactionalMethod() {
        // AOP逻辑
        target.transactionalMethod();
        // AOP逻辑
    }
}
```

### JDK动态代理
```java
class UserServiceInvocationHandler implements InvocationHandler {
    private UserServiceImpl target; // 目标对象存储在InvocationHandler中
    
    public Object invoke(Object proxy, Method method, Object[] args) {
        // AOP逻辑
        return method.invoke(target, args);
        // AOP逻辑
    }
}
```

## 验证方法

运行 `testDemonstrateTargetObjectRetrieval()` 测试，观察：

1. **代理对象类型**：包含 `$$EnhancerBySpringCGLIB$$`
2. **目标对象类型**：原始的 `UserServiceImpl`
3. **目标对象调用**：事务失效，数据会插入

## 关键理解

### 1. 容器中只有代理对象
```java
// Spring容器中注册的是代理对象
UserService userService = context.getBean(UserService.class);
// userService 是代理对象，不是目标对象
```

### 2. 目标对象被代理对象持有
```java
// 目标对象作为代理对象的字段存在
class Proxy {
    private Target target; // 目标对象存储在这里
}
```

### 3. 获取目标对象需要代理对象
```java
// 必须通过代理对象来获取目标对象
Object target = getTargetObject(proxy);
```

### 4. 目标对象调用事务失效
```java
// 通过目标对象调用方法，事务失效
target.transactionalMethod(); // 无事务保护
```

## 实际应用场景

### 1. 调试和测试
```java
// 在测试中获取目标对象进行调试
@Test
void testTargetObject() {
    UserService proxy = context.getBean(UserService.class);
    UserService target = (UserService) AopUtils.getTargetObject(proxy);
    
    // 直接调用目标对象方法
    target.someMethod();
}
```

### 2. 绕过AOP
```java
// 在某些特殊情况下需要绕过AOP
public void someMethod() {
    UserService target = (UserService) AopUtils.getTargetObject(this);
    target.transactionalMethod(); // 绕过AOP，事务失效
}
```

### 3. 性能优化
```java
// 在循环中避免重复的AOP开销
public void batchProcess() {
    UserService target = (UserService) AopUtils.getTargetObject(this);
    for (int i = 0; i < 1000; i++) {
        target.processItem(i); // 直接调用目标对象，无AOP开销
    }
}
```

## 总结

**目标对象获取的关键点**：

1. **必须通过代理对象获取**：目标对象存储在代理对象内部
2. **使用Spring工具类**：`AopUtils.getTargetObject()` 是最安全的方法
3. **目标对象调用无AOP**：通过目标对象调用方法会绕过AOP机制
4. **谨慎使用**：目标对象调用会失去AOP功能，需要谨慎使用

**记忆口诀**：
- 容器中只有代理对象
- 目标对象在代理对象内部
- 获取目标对象需要代理对象
- 目标对象调用无AOP保护
