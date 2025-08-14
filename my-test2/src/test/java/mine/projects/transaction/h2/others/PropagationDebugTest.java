package mine.projects.transaction.h2.others;

import mine.projects.transaction.h2.others.config.TransactionConfig;
import mine.projects.transaction.h2.others.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * 25.08.14 Thu
 * 测试事务传播属性。
 */
public class PropagationDebugTest {
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(TransactionConfig.class);
        
        UserService userService = context.getBean(UserService.class);
        
        // 测试点1: REQUIRED传播
        try {
            userService.createUserWithRequired();
        } catch (Exception e) {
            System.out.println("===>REQUIRED 测试失败: " + e.getMessage());
        }
        
        // 测试点2: REQUIRES_NEW传播
        try {
            userService.createUserWithRequiresNew();
        } catch (Exception e) {
            System.out.println("===>REQUIRES_NEW 测试失败: " + e.getMessage());
        }
        
        // 测试点3: NESTED传播
        try {
            userService.createUserWithNested();
        } catch (Exception e) {
            System.out.println("===>NESTED 测试失败: " + e.getMessage());
        }
        
        // 检查数据库状态
        JdbcTemplate jdbc = context.getBean(JdbcTemplate.class);
        System.out.println("===>Users: " + jdbc.queryForList("SELECT * FROM users"));
		List<Map<String, Object>> userListe= jdbc.queryForList("SELECT * FROM users");


		System.out.println("===>Audit Logs: " + jdbc.queryForList("SELECT * FROM audit_log"));
        
        context.close();
    }
}