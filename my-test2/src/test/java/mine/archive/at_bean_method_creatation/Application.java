package mine.archive.at_bean_method_creatation;

import mine.archive.at_bean_method_creatation.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试 @Bean 方法之间的调用。
 * @date 25.07.27 Sun
 */
public class Application {

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MyConfig.class);

		context.close(); // 显式关闭 context
	}
}