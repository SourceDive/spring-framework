package mine.jdk.debug.thread;

/**
 * @author zero
 * @description 触发中断和恢复中断。
 * 抛出异常之后再去调用就是恢复，如果是正常流程直接调用就是触发
 * @date 2025-11-22
 */
public class InterruptDemo {
	public static void main(String[] args) {
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		// 没有触发中断前打印是否中断标志
		boolean interrupted = Thread.currentThread().isInterrupted();
		System.out.println(interrupted);

		Thread.currentThread().interrupt();

		// 触发中断之后打印中断标志。
		interrupted = Thread.currentThread().isInterrupted();
		System.out.println(interrupted);
	}
}
