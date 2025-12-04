package mine.jdk.debug.thread.static_field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zero
 * @description 验证静态字段全局共享。
 * @date 2025-12-04
 */
public class Demo01 {
	static List<String> list = Collections.synchronizedList(new ArrayList<String>());

	public void exec() {
		list.add(Thread.currentThread().getName());
		System.out.println(System.identityHashCode(list));
	}
}
