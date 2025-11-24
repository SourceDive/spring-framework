package mine.archive.annotation_autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author zero
 * @description @Autowired流程验证
 * @date 25.11.24 Mon
 */
public class AutowiredDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(Cat.class, Person.class); // 01、隐式注册

//		context.register(Cat.class); // 02、显式注册，显式刷新
//		context.register(Person.class);
//		context.refresh();

		System.out.println("========用户逻辑开始执行========");
		System.out.println("Bean定义名称: " + Arrays.toString(context.getBeanDefinitionNames()));

		Person person = context.getBean(Person.class);
		person.invoke();
	}
}
