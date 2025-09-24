package mine.projects.circular_dependency_aop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 25.09.23 Tue
 */
@Component
public class Person {
	@Autowired
//	@Lazy
    Cat cat;

	public void invoke() {
		cat.execute();
	}
}
