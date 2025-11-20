package mine.archive.annotation_bean.lite_mode;

import mine.archive.annotation_bean.lite_mode.config.MyComponent;
import mine.archive.annotation_bean.lite_mode.config.MyComponent2;
import mine.archive.annotation_bean.lite_mode.service.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试 @Bean lite 模式，方法声明在@Component类中
 * @date 25.11.20 Thu
 */
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MyComponent.class,
						MyComponent2.class);

		MyService myService1 = context.getBean(MyService.class);
		myService1.execute();
		MyService myService2 = context.getBean(MyService.class);

		context.close(); // 显式关闭 context
	}
}