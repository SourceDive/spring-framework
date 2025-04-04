package com.ongoing.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zero
 * @description todo
 * @date 2025-04-02
 */
public class Application {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		Hello hello = (Hello) ac.getBean("hello");
		hello.sayHello();
	}
}