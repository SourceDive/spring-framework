package com.ongoing.demo.circular_dependency.autowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试循环依赖 基于注解类型的
 * @date 2025-04-06
 */
public class ApplicationDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("com.ongoing.demo.circular_dependency.autowired");
	}
}
