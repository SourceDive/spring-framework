package mine.archive.new_instance;

import org.junit.jupiter.api.Test;

/**
 * @author zero
 * @description 测试 newInstance() 方法
 * 注意，测试类以Test/Tests为结尾。
 * @date 2025-07-29
 */
public class NewInstanceTest {

	@Test
	public void testNewInstance() throws InstantiationException,
			IllegalAccessException {
		Cat cat = Cat.class.newInstance();
		System.out.println("===>" + cat.toString());
	}
}
