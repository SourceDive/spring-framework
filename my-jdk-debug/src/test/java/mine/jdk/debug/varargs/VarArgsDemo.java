package mine.jdk.debug.varargs;

/**
 * @author zero
 * @description 测试可变参数
 * @date 2025-06-06
 */
public class VarArgsDemo {

	public static void exec(String... params) { // 这里就相当于 String[] params
		System.out.println(params.length);
		for (String str: params) {
			System.out.println(str);
		}

	}

	public static void main(String[] args) {
		exec("01", "02");
	}
}
