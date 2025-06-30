package com.ongoing.demo.circular_dependency.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试FactoryBean的循环依赖
 * @date 25.06.11 Wed
 */
public class ApplicationDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("com.ongoing.demo.circular_dependency.factorybean");
	}
}
