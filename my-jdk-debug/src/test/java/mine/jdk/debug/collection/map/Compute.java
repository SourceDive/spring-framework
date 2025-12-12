package mine.jdk.debug.collection.map;

/**
 * @author zero
 * @description n * 0.75 的表示
 * @date 2025-12-11
 */
public class Compute {
	public static void main(String[] args) {
		int n = 16;
		System.out.println(n >>> 2);

		System.out.println(n - (n >>> 2));
	}
}
