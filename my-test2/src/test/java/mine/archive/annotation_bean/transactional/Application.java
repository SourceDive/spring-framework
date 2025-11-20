package mine.archive.annotation_bean.transactional;

import mine.archive.annotation_bean.transactional.config.MyConfig;
import mine.archive.annotation_bean.transactional.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 创建事务管理器，没有DataSource会报错。从这个测试类中可以得到。
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