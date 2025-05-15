package com.ongoing.demo.circular_dependency.self_injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description 测试循环依赖 基于 @Autowired 注入自己
 * @date 25.05.14 Wed
 */
public class ApplicationDemo {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("com.ongoing.demo.circular_dependency.mine");
	}
}
