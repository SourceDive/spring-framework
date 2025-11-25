package mine.jdk.debug;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zero
 * @description 测试可变对象。
 * @date 2025-11-05
 */
public class ImmutableDemo {
	private final Set<String> set = new HashSet<>();

	public ImmutableDemo() {
		set.add("hello");
		set.add("hello1");
	}

	public static void main(String[] args) {
		ImmutableDemo demo = new ImmutableDemo();
		demo.set.add("hello");

		System.out.println(demo.set);
	}
}
