package mine.jdk.debug.close;

import java.util.function.IntSupplier;

/**
 * @author zero
 * @description 最简单的闭包示例：演示 Lambda 表达式如何捕获外部变量
 * @date 25.12.22 Mon
 */
public class SimpleClosureDemo {
	
	public static void main(String[] args) {
		System.out.println("========== 最简单的闭包示例 ==========\n");
		
		// 示例1：闭包捕获局部变量
		int x = 10;  // 外部变量
		
		// Lambda 表达式捕获了外部变量 x，这就是闭包
		IntSupplier closure = () -> {
			// 即使 createClosure 方法执行完毕，这个 Lambda 仍然可以访问 x
			return x * 2;
		};

		// 调用闭包函数
		int result = closure.getAsInt();
		System.out.println("示例1 - 闭包捕获外部变量:");
		System.out.println("  外部变量 x = " + x);
		System.out.println("  闭包执行结果: " + result);
		
		// 示例2：闭包可以访问修改后的变量（必须是 final 或 effectively final）
		int y = 5;
		IntSupplier closure2 = () -> y + 10;  // y 必须是 effectively final
		
		System.out.println("\n示例2 - 闭包访问 effectively final 变量:");
		System.out.println("  外部变量 y = " + y);
		System.out.println("  闭包执行结果: " + closure2.getAsInt());
		
		// 示例3：闭包在方法返回后仍然有效
		IntSupplier closure3 = createClosure(20);
		System.out.println("\n示例3 - 方法返回后闭包仍然有效:");
		System.out.println("  闭包执行结果: " + closure3.getAsInt());
	}
	
	/**
	 * 创建一个闭包并返回
	 * 即使这个方法执行完毕，返回的闭包仍然可以访问参数 value
	 */
	public static IntSupplier createClosure(int value) {
		// Lambda 捕获了方法参数 value，形成闭包
		return () -> value * 3;
	}
}

