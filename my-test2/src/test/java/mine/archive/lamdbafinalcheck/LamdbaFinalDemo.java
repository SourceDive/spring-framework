package mine.archive.lamdbafinalcheck;

import java.lang.reflect.Modifier;

/**
 * @author zero
 * @description 这里用例子检查lambda生成的类是否是final的
 * @date 2025-05-27
 */
public class LamdbaFinalDemo {
	public static void main(String[] args) {
		Runnable lamdba = () -> System.out.println("test");
		Class<? extends Runnable> aClass = lamdba.getClass();

		System.out.println("====");
		boolean isFinal = Modifier.isFinal(aClass.getModifiers());
		System.out.println("isFinal: " + isFinal);
		System.out.println(aClass.getName());
	}
}
