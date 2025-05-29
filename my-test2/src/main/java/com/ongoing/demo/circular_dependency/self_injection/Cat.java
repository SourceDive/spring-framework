package com.ongoing.demo.circular_dependency.self_injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-04-06
 */
@Component
public class Cat {
//	@Autowired
//	Cat cat;
	public Cat(Cat cat) {}
}
