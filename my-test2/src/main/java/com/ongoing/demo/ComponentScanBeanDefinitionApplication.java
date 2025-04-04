package com.ongoing.demo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zero
 * @description 获取bean元信息的一个例子
 * @date 2025-04-04
 */
public class ComponentScanBeanDefinitionApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext("com.ongoing.demo" +
						".domain"); // 参数是person所在的包全限定名
		BeanDefinition person = ctx.getBeanDefinition("person");
		System.out.println(person);
		System.out.println(person.getClass().getName());
	}
}