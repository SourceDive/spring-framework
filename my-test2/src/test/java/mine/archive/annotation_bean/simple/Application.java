package mine.archive.annotation_bean.simple;

import mine.archive.annotation_bean.simple.config.MyConfig;
import mine.archive.annotation_bean.simple.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试 @Bean 注解
 * @date 25.07.23 Wed
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MyConfig.class);

		MyService myService = context.getBean(MyService.class);
		myService.execute();

		context.close(); // 显式关闭 context
	}
}