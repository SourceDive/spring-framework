package com.ongoing.demo.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zero
 * @description todo
 * @date 2025-04-04
 */
@Configuration
public class BeanDefinitionConfiguration {

	@Bean
	public Person person() {
		return new Person();
	}
}
