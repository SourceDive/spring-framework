package com.ongoing.demo;

import com.ongoing.demo.domain.BeanDefinitionConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 基于@Bean的BeanDefinition
 * @date 2025-04-04
 */
public class AnnotationConfigBeanDefinitionApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(BeanDefinitionConfiguration.class);
		BeanDefinition person = ctx.getBeanDefinition("person");
		System.out.println(person);
		System.out.println(person.getClass().getName());
	}
}