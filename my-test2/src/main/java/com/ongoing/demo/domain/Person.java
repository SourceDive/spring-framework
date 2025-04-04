package com.ongoing.demo.domain;

import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description
 * @date 2025-04-04
 */
@Component
public class Person {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
