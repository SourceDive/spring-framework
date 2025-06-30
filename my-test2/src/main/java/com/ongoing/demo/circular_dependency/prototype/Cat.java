package com.ongoing.demo.circular_dependency.prototype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-04-06
 */
@Component
@Scope("prototype")
public class Cat {
	@Autowired
    Person person;
}
