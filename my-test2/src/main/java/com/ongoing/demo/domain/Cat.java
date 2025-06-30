package com.ongoing.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-05-08
 */
@Component
public class Cat {

	@Autowired
	private Person person;
}
