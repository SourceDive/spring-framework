package mine.archive.constructor_new_instance;

/**
 * @author zero
 * @description debug new 关键字
 * 本来打算是这样的，但是似乎这个方案不太行，还需要看去hotspot jvm的源码
 * 这个包给归档掉吧。
 * @date 2025-06-11
 */
public class ConstructorNewInstanceDemo {
	public static void main(String[] args) {
		Cat cat = new Cat();
		System.out.println(cat);
	}
}
