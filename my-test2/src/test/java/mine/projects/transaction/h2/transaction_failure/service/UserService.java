package mine.projects.transaction.h2.transaction_failure.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务接口
 * 演示各种事务失效的场景
 */
public interface UserService {
    
    /**
     * 正常的事务方法
     */
    @Transactional
    void createUserSuccess(String username);
    
    /**
     * 事务方法中抛出异常 - 应该回滚
     */
    @Transactional
    void createUserWithException(String username);
    
    /**
     * 非public方法上的@Transactional - 事务失效
     */
    @Transactional
    void createUserPrivateMethod(String username);
    
    /**
     * 同一个类内部调用事务方法 - 事务失效
     */
    void callTransactionalMethodInternally(String username);
    
    /**
     * 事务方法中捕获异常 - 事务失效
     */
    @Transactional
    void createUserWithCaughtException(String username);
    
    /**
     * 非RuntimeException异常 - 事务失效（默认只回滚RuntimeException）
     */
    @Transactional
    void createUserWithCheckedException(String username) throws Exception;
    
    /**
     * 静态方法上的@Transactional - 事务失效
     */
    @Transactional
    static void createUserStaticMethod(String username) {
        // 静态方法无法被代理
    }
    
    /**
     * final方法上的@Transactional - 事务失效
     */
    @Transactional
    void createUserFinalMethod(String username);
    
    /**
     * 没有@Transactional注解的方法调用事务方法 - 事务失效
     */
    void callTransactionalMethodWithoutAnnotation(String username);
    
    /**
     * 事务传播级别为NOT_SUPPORTED - 事务失效
     */
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED)
    void createUserWithNotSupported(String username);
    
    /**
     * 事务传播级别为NEVER - 事务失效
     */
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.NEVER)
    void createUserWithNever(String username);
    
    /**
     * 事务传播级别为SUPPORTS且当前没有事务 - 事务失效
     */
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.SUPPORTS)
    void createUserWithSupports(String username);
    
    /**
     * 事务传播级别为MANDATORY且当前没有事务 - 抛出异常
     */
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.MANDATORY)
    void createUserWithMandatory(String username);
    
    /**
     * 事务传播级别为REQUIRES_NEW - 会创建新事务
     */
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    void createUserWithRequiresNew(String username);
    
    /**
     * 事务传播级别为NESTED - 会创建嵌套事务
     */
    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.NESTED)
    void createUserWithNested(String username);
    
    /**
     * 获取所有用户名
     */
    java.util.List<String> getAllUserNames();
}
