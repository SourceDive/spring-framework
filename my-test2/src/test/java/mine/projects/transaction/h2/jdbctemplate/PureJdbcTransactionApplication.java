package mine.projects.transaction.h2.jdbctemplate;

import mine.projects.transaction.h2.jdbctemplate.config.TransactionConfig;
import mine.projects.transaction.h2.jdbctemplate.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试简单事务机制(使用纯spring jdbc, 不引入第三方库)
 * @date 2025-07-10
 */
public class PureJdbcTransactionApplication {

	private static final Logger logger = LoggerFactory.getLogger(PureJdbcTransactionApplication.class);

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(TransactionConfig.class);

		// 2. 获取代理后的服务对象
		UserDao userDao = context.getBean(UserDao.class);

		// 3. 调用事务方法（在此处设置断点）
		userDao.createUser("debugUser01");

//		userDao.createUser("debugUser02");

		logger.info("===> {}", userDao.getAllUserName().toString());

		context.close(); // 显式关闭 context
	}
}