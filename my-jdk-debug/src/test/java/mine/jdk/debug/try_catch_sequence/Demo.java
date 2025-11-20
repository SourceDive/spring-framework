package mine.jdk.debug.try_catch_sequence;

/**
 * @author zero
 * @description 查看finally里，在catch抛出异常后，是否会执行。
 * ===> 会执行。
 * @date 2025-08-19
 */
public class Demo {
	public static void main(String[] args) {
		try {
			if (1 == 1) {
				throw new RuntimeException("try 抛出异常");
			}
		} catch (Exception e) {
			if (1 == 1) {
				System.out.println("exception");
			}
			throw new RuntimeException("try 抛出异常");
		} finally {
			System.out.println("===>end");
		}

		System.out.println("finally完毕之后"); // 不会被执行。
	}
}
