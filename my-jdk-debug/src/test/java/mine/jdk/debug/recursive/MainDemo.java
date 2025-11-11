package mine.jdk.debug.recursive;

/**
 * @author zero
 * @description main方法的递归，会导致栈溢出。
 * @date 2025-11-07
 */
public class MainDemo {
	public static void main(String[] args) {
		main(new String[]{});
	}
}
