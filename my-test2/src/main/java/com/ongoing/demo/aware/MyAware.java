package com.ongoing.demo.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zero
 * @description todo
 * @date 2025-04-15
 */
public class MyAware implements ApplicationContextAware {
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("===>hhhh, 我这里实现了ApplicationContextAware");
	}
}
