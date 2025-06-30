package com.ongoing.demo.circular_dependency.prototype;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试循环依赖 基于原型作用域
 * @date 2025-04-15
 */
public class ApplicationDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("com.ongoing.demo.circular_dependency.prototype");
	}
}
