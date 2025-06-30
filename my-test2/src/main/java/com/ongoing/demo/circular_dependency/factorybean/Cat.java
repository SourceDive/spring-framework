package com.ongoing.demo.circular_dependency.factorybean;

import org.springframework.stereotype.Component;

@Component
public class Cat {
	// 依赖Person
	public Cat(Person person) {
	}
}
