package mine.projects.transaction.h2.transaction_failure;

import mine.projects.transaction.h2.transaction_failure.config.TransactionFailureConfig;
import mine.projects.transaction.h2.transaction_failure.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * 事务失效调试应用程序
 * 提供各种事务失效场景的调试入口
 */
public class TransactionFailureDebugApplication {
    
    public static void main(String[] args) {
        System.out.println("=== 事务失效场景调试程序 ===");
        
        // 初始化Spring上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TransactionFailureConfig.class);
        UserService userService = context.getBean(UserService.class);
        
        // 你可以在这里设置断点进行调试
        System.out.println("\n1. 正常事务方法 - 在createUserSuccess方法设置断点");
        userService.createUserSuccess("debug_user1");
        
        System.out.println("\n2. 异常事务方法 - 在createUserWithException方法设置断点");
        try {
            userService.createUserWithException("debug_user2");
        } catch (RuntimeException e) {
            System.out.println("捕获异常: " + e.getMessage());
        }
        
        System.out.println("\n3. 内部调用事务方法 - 在callTransactionalMethodInternally方法设置断点");
        userService.callTransactionalMethodInternally("debug_user3");
        
        System.out.println("\n4. 捕获异常事务方法 - 在createUserWithCaughtException方法设置断点");
        userService.createUserWithCaughtException("debug_user4");
        
        System.out.println("\n5. 检查异常事务方法 - 在createUserWithCheckedException方法设置断点");
        try {
            userService.createUserWithCheckedException("debug_user5");
        } catch (Exception e) {
            System.out.println("捕获检查异常: " + e.getMessage());
        }
        
        System.out.println("\n6. final方法事务 - 在createUserFinalMethod方法设置断点");
        userService.createUserFinalMethod("debug_user6");
        
        System.out.println("\n7. NOT_SUPPORTED传播 - 在createUserWithNotSupported方法设置断点");
        userService.createUserWithNotSupported("debug_user7");
        
        System.out.println("\n8. NEVER传播 - 在createUserWithNever方法设置断点");
        userService.createUserWithNever("debug_user8");
        
        System.out.println("\n9. SUPPORTS传播 - 在createUserWithSupports方法设置断点");
        userService.createUserWithSupports("debug_user9");
        
        System.out.println("\n10. MANDATORY传播 - 在createUserWithMandatory方法设置断点");
        try {
            userService.createUserWithMandatory("debug_user10");
        } catch (Exception e) {
            System.out.println("捕获MANDATORY异常: " + e.getMessage());
        }
        
        System.out.println("\n11. REQUIRES_NEW传播 - 在createUserWithRequiresNew方法设置断点");
        userService.createUserWithRequiresNew("debug_user11");
        
        System.out.println("\n12. NESTED传播 - 在createUserWithNested方法设置断点");
        userService.createUserWithNested("debug_user12");
        
        // 显示最终结果
        List<String> users = userService.getAllUserNames();
        System.out.println("\n=== 最终用户列表 ===");
        users.forEach(System.out::println);
        
        context.close();
    }
}
