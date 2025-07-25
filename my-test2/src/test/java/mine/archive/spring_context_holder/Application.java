package mine.archive.spring_context_holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试简单事务机制(使用纯spring jdbc, 不引入第三方库)
 * @date 2025-07-10
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext("mine.archive.spring_context_holder");

		Object userDao = SpringContextHolder.getBean("userDao");
		System.out.println("userDao = " + userDao);

		context.close(); // 显式关闭 context
	}
}