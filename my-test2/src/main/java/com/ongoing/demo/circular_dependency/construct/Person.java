package com.ongoing.demo.circular_dependency.construct;

import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-04-06
 */
@Component
public class Person {
	private final Cat cat;

	public Person(Cat cat) {
		this.cat = cat;
	}


}
