package com.ongoing.demo.circular_dependency.construct;

import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-04-06
 */
@Component
public class Cat {
	private final Person person;

	public Cat(Person person) {
		this.person = person;
	}
}
