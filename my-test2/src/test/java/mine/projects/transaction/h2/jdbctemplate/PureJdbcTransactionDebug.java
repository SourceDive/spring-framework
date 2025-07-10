package mine.projects.transaction.h2.jdbctemplate;

import mine.projects.transaction.h2.jdbctemplate.config.TransactionConfig;
import mine.projects.transaction.h2.jdbctemplate.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试简单事务机制(使用纯spring jdbc, 不引入第三方库)
 * @date 2025-07-10
 */
public class PureJdbcTransactionDebug {

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(TransactionConfig.class);

		// 2. 获取代理后的服务对象
		UserService userService = context.getBean(UserService.class);

		// 3. 调用事务方法（在此处设置断点）
		userService.createUser("debugUser");

		context.close(); // 显示关闭 context
	}
}