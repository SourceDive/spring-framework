package mine.projects.annotation_value;

import mine.projects.annotation_value.config.MyConfig;
import mine.projects.annotation_value.service.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 最简单的@Value注解使用示例，用于学习Spring源码流程
 * @date 25.11.19 Wed
 */
public class ValueDemo {

	public static void main(String[] args) {
		System.out.println("========== @Value注解源码学习入口 ==========\n");

		// 创建Spring应用上下文
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MyConfig.class);

		// 获取Bean实例
		MyService bean = context.getBean(MyService.class);

		// 打印注入的值
		System.out.println("1. 直接字符串注入: " + bean.getSimpleValue());
		System.out.println("2. 占位符注入: " + bean.getAppName());
		System.out.println("3. SpEL表达式注入: " + bean.getCalculatedValue());

		context.close();
	}
}

