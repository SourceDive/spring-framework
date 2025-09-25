package mine.projects.objenesis.configuration_full_and_lite;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试 @Configuration full 和 lite 模式
 * @date 2025-06-18
 */
public class ApplicationDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext("mine.projects.configuration_full_and_lite");
		ServiceA serviceA = context.getBean(ServiceA.class);
		ServiceB serviceB = context.getBean(ServiceB.class);
		ServiceB bInA = serviceA.getServiceB();
		System.out.println(serviceA);
		System.out.println(serviceB);
		System.out.println(bInA);

	}
}
