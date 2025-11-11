package mine.archive.custom_scope;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试自定义 scope
 * @date 2025-10-08
 */
public class Application {

	public static void main(String[] args) {
		// 1、创建容器
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext();

		// 2、注册scope
		context.getBeanFactory().registerScope("one", new OneScope());
		context.register(MyConfig.class);
		context.refresh();

		// 3、多次获取 Bean
		Student myBean = context.getBean(Student.class);
		System.out.println("==>");
		System.out.println(myBean.getName());
		Student myBean2 = context.getBean(Student.class);
		System.out.println(myBean2.getName());
	}
}
