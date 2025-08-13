package mine.archive.functional_interface.only_one_method;

/**
 * @author zero
 * @description 测试：只有一个方法的抽象接口就是函数式接口。
 * @date 2025-08-13
 */
public class OneMethodDemo {
	public static void main(String[] args) {
		OneMethod oneMethod = () -> {};
		oneMethod.exec();
	}
}
