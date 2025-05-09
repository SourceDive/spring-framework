package com.ongoing.demo;

import com.ongoing.demo.domain.Hello;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zero
 * @description todo
 * @date 2025-04-02
 */
public class Application {
	public static void main(String[] args) {
//		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		Hello hello = (Hello) ac.getBean("hello");
//		hello.sayHello();

		// 方式一
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.ongoing.demo.domain");
		System.out.println(context);

		// 方式二
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//		context.scan("");
//		context.refresh();

	}
}