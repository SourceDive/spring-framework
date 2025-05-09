package mine;

/**
 * @author zero
 * @description idea 调试堆栈查看 <init>条目
 * @date 2025-05-09
 */
public class DebugInitDemo {
	{
		System.out.println("初始化代码块");
	}

	public DebugInitDemo() {
		System.out.println("构造函数");
	}

	public static void main(String[] args) {
		new DebugInitDemo();
	}
}
