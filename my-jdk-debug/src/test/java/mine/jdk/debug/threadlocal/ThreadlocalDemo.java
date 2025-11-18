package mine.jdk.debug.threadlocal;

/**
 * @author zero
 * @description ThreadLocal 测试
 * @date 2025-11-17
 */
public class ThreadlocalDemo {
	public static void main(String[] args) {
		ThreadLocal<String> threadLocal01 = new ThreadLocal<>();
		ThreadLocal<String> threadLocal02 = new ThreadLocal<>();

		threadLocal01.set("111");
		threadLocal02.set("222");

		threadLocal01.remove();

		System.out.println(threadLocal01.get());

	}
}
