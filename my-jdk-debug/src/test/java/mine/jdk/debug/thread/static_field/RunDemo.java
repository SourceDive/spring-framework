package mine.jdk.debug.thread.static_field;

/**
 * @author zero
 * @description todo
 * @date 2025-12-04
 */
public class RunDemo {
	public static void main(String[] args) {
		Runnable task = () -> {
			new Demo01().exec();
		};

		new Thread(task).start();
		new Thread(task).start();
		new Thread(task).start();
	}
}
