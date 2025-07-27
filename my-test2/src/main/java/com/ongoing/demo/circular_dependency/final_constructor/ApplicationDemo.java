package com.ongoing.demo.circular_dependency.final_constructor;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description 测试循环依赖 final字段。预期：直接报错。
 * @date 25.07.27 Sun
 */
public class ApplicationDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("com.ongoing.demo.circular_dependency.final_constructor");
	}
}
