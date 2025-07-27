package com.ongoing.demo.circular_dependency.final_constructor;

import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 25.07.27 Sun
 */
@Component
public class Person {
	private final Cat cat;

	public Person(Cat cat) {
		this.cat = cat;
	}
}
