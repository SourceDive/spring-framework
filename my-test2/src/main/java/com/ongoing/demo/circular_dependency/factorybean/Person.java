package com.ongoing.demo.circular_dependency.factorybean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-04-06
 */
public class Person {
	@Autowired
    Cat cat;
}
