package mine.archive.custom_scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-10-08
 */
@Component
@Scope("one")
public class Student {
	private String name;
	private int index;

	public Student(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
