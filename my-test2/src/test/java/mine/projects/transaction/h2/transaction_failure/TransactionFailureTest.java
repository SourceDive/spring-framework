package mine.projects.transaction.h2.transaction_failure;

import mine.projects.transaction.h2.transaction_failure.config.TransactionFailureConfig;
import mine.projects.transaction.h2.transaction_failure.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.IllegalTransactionStateException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 事务失效测试类
 * 演示各种导致事务失效的场景
 */
public class TransactionFailureTest {
    
    private UserService userService;
    private AnnotationConfigApplicationContext context;
    
    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(TransactionFailureConfig.class);
        userService = context.getBean(UserService.class);
    }
    
    /**
     * 测试1: 正常事务方法 - 应该成功
     */
    @Test
    void testCreateUserSuccess() {
        System.out.println("\n=== 测试1: 正常事务方法 ===");
        userService.createUserSuccess("user1");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user1"));
    }
    
    /**
     * 测试2: 事务方法中抛出异常 - 应该回滚
     */
    @Test
    void testCreateUserWithException() {
        System.out.println("\n=== 测试2: 事务方法中抛出异常 ===");
        try {
            userService.createUserWithException("user2");
        } catch (RuntimeException e) {
            System.out.println("捕获异常: " + e.getMessage());
        }
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertFalse(users.contains("user2"));
    }
    
    /**
     * 测试3: 非public方法上的@Transactional - 事务失效
     */
    @Test
    void testCreateUserPrivateMethod() {
        System.out.println("\n=== 测试3: 非public方法上的@Transactional ===");
        userService.createUserPrivateMethod("user3");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        // 注意：这里实际上还是public方法，只是演示概念
        assertTrue(users.contains("user3"));
    }
    
    /**
     * 测试4: 同一个类内部调用事务方法 - 事务失效
     */
    @Test
    void testCallTransactionalMethodInternally() {
        System.out.println("\n=== 测试4: 同一个类内部调用事务方法 ===");
        userService.callTransactionalMethodInternally("user4");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user4_internal"));
    }
    
    /**
     * 测试5: 事务方法中捕获异常 - 事务失效
     */
    @Test
    void testCreateUserWithCaughtException() {
        System.out.println("\n=== 测试5: 事务方法中捕获异常 ===");
        userService.createUserWithCaughtException("user5");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        // 异常被捕获，事务不会回滚
        assertTrue(users.contains("user5"));
    }
    
    /**
     * 测试6: 非RuntimeException异常 - 事务失效（默认只回滚RuntimeException）
     */
    @Test
    void testCreateUserWithCheckedException() {
        System.out.println("\n=== 测试6: 非RuntimeException异常 ===");
        try {
            userService.createUserWithCheckedException("user6");
        } catch (Exception e) {
            System.out.println("捕获检查异常: " + e.getMessage());
        }
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        // 检查异常默认不会回滚
        assertTrue(users.contains("user6"));
    }
    
    /**
     * 测试7: 静态方法上的@Transactional - 事务失效
     */
    @Test
    void testCreateUserStaticMethod() {
        System.out.println("\n=== 测试7: 静态方法上的@Transactional ===");
        UserService.createUserStaticMethod("user7");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        // 静态方法无法被代理，事务失效
        assertFalse(users.contains("user7"));
    }
    
    /**
     * 测试8: final方法上的@Transactional - 事务失效
     */
    @Test
    void testCreateUserFinalMethod() {
        System.out.println("\n=== 测试8: final方法上的@Transactional ===");
        userService.createUserFinalMethod("user8");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        // final方法无法被代理，事务失效
        assertFalse(users.contains("user8"));
    }
    
    /**
     * 测试9: 没有@Transactional注解的方法调用事务方法 - 事务失效
     */
    @Test
    void testCallTransactionalMethodWithoutAnnotation() {
        System.out.println("\n=== 测试9: 没有@Transactional注解的方法调用事务方法 ===");
        userService.callTransactionalMethodWithoutAnnotation("user9");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user9_no_annotation"));
    }
    
    /**
     * 测试10: 事务传播级别为NOT_SUPPORTED - 事务失效
     */
    @Test
    void testCreateUserWithNotSupported() {
        System.out.println("\n=== 测试10: 事务传播级别为NOT_SUPPORTED ===");
        userService.createUserWithNotSupported("user10");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user10"));
    }
    
    /**
     * 测试11: 事务传播级别为NEVER - 事务失效
     */
    @Test
    void testCreateUserWithNever() {
        System.out.println("\n=== 测试11: 事务传播级别为NEVER ===");
        userService.createUserWithNever("user11");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user11"));
    }
    
    /**
     * 测试12: 事务传播级别为SUPPORTS且当前没有事务 - 事务失效
     */
    @Test
    void testCreateUserWithSupports() {
        System.out.println("\n=== 测试12: 事务传播级别为SUPPORTS且当前没有事务 ===");
        userService.createUserWithSupports("user12");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user12"));
    }
    
    /**
     * 测试13: 事务传播级别为MANDATORY且当前没有事务 - 抛出异常
     */
    @Test
    void testCreateUserWithMandatory() {
        System.out.println("\n=== 测试13: 事务传播级别为MANDATORY且当前没有事务 ===");
        assertThrows(IllegalTransactionStateException.class, () -> {
            userService.createUserWithMandatory("user13");
        });
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertFalse(users.contains("user13"));
    }
    
    /**
     * 测试14: 事务传播级别为REQUIRES_NEW - 会创建新事务
     */
    @Test
    void testCreateUserWithRequiresNew() {
        System.out.println("\n=== 测试14: 事务传播级别为REQUIRES_NEW ===");
        userService.createUserWithRequiresNew("user14");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user14"));
    }
    
    /**
     * 测试15: 事务传播级别为NESTED - 会创建嵌套事务
     */
    @Test
    void testCreateUserWithNested() {
        System.out.println("\n=== 测试15: 事务传播级别为NESTED ===");
        userService.createUserWithNested("user15");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user15"));
    }
    
    /**
     * 测试16: 在事务中调用REQUIRES_NEW方法
     */
    @Test
    void testTransactionalMethodCallingRequiresNew() {
        System.out.println("\n=== 测试16: 在事务中调用REQUIRES_NEW方法 ===");
        userService.createUserSuccess("user16_1");
        userService.createUserWithRequiresNew("user16_2");
        
        List<String> users = userService.getAllUserNames();
        System.out.println("当前用户列表: " + users);
        assertTrue(users.contains("user16_1"));
        assertTrue(users.contains("user16_2"));
    }
}
