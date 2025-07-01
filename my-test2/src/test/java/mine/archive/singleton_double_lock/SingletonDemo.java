package mine.archive.singleton_double_lock;

/**
 * @author zero
 * @description 单例，双锁检测
 * @date 2025-07-01
 */
public class SingletonDemo {

	private static volatile SingletonDemo instance;

	private SingletonDemo() {}

	public static SingletonDemo getInstance() {
		// 加锁前判断一次，防止不必要的加锁，缩小锁的范围
		if (instance == null) {
			synchronized (SingletonDemo.class) {
				// 预防其他线程也创建的实例，再次判断
				if (instance == null) {
					instance = new SingletonDemo();
				}
			}
		}
		return instance;
	}
}
