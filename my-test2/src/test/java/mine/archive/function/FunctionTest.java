package mine.archive.function;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

/**
 * @author zero
 * @description 测试 jdk 函数式接口。
 * @date 2025-09-26
 */
public class FunctionTest {

	@Test
	public void testFunction() {
		Function<Integer, Integer> function = (x) -> x * x;
		Integer result = function.apply(1);
		System.out.println(result);
	}

}
