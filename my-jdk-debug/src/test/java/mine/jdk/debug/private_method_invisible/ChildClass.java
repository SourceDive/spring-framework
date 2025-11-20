package mine.jdk.debug.private_method_invisible;

/**
 * @author zero
 * @description 子类看不到父类的private方法
 * @date 2025-06-06
 */
public class ChildClass extends ParentClass {
	public void method2() {
//		super.method2();

	}
}
