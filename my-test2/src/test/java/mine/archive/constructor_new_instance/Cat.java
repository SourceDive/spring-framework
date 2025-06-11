package mine.archive.constructor_new_instance;

/**
 * @author zero
 * @description todo
 * @date 2025-06-11
 */
public class Cat {
	private String name;
	private String id;

	public Cat() {
		System.out.println("查看这里的调用栈"); // 调试完发现调用栈并没有看到什么有用的信息
	}
}
