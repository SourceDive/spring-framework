package mine.archive.annotation_autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cat {
	public void miao() {
		System.out.println("猫叫了。");
	}
}
