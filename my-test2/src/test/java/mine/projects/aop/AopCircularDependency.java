package mine.projects.aop;

import mine.projects.aop.service.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author zero
 * @description 涉及到代理对象的循环引用。
 * @date 2025-09-23
 */
public class AopCircularDependency {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext(TxConfig.class);
		System.out.println("Bean定义名称: " + Arrays.toString(applicationContext.getBeanDefinitionNames()));
		
		// 获取Person bean并调用，触发循环依赖和事务
		Person person = applicationContext.getBean(Person.class);
		System.out.println("Person bean类型: " + person.getClass().getName());
		System.out.println("开始调用Person.invoke()...");
		person.invoke();
	}
}
