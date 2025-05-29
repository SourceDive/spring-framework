package com.ongoing.demo.circular_dependency.constructor;

import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-04-06
 */
@Component
public class Person {

	public Person(Cat cat) {
	}
}
