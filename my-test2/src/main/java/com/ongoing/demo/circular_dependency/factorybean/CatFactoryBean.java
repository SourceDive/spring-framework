package com.ongoing.demo.circular_dependency.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zero
 * @description todo
 * @date 2025-06-11
 */
@Service
public class CatFactoryBean implements FactoryBean<Cat> {
	@Autowired
	private Person person;

	@Override
	public Cat getObject() throws Exception {
		return new Cat(person);
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}
}
