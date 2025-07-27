package com.ongoing.demo.circular_dependency.final_constructor;

import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 25.07.27 Sun
 */
@Component
public class Cat {
	private final Person person;

	public Cat(Person person) {
		this.person = person;
	}
}
