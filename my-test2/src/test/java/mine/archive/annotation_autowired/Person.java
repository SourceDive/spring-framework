package mine.archive.annotation_autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
	@Autowired
    Cat cat;

	public void invoke() {
		cat.miao();
	}
}
