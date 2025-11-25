package mine.jdk.debug.annotation;

/**
 * @author zero
 * @description 测试注解能否作为方法的参数
 *
 * result: 下面是可以编译成功的。
 * @date 2025-11-25
 */
public class MyAnnotationDemo {
	void method(MyAnnotation annotation) {

	}

	public static void main(String[] args) {
		System.out.println("");
	}
}
