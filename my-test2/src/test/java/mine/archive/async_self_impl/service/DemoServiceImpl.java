package mine.archive.async_self_impl.service;

/**
 * @author zero
 * @description todo
 * @date 2025-10-11
 */
public class DemoServiceImpl implements DemoService {
	@Override
	public void perform() throws InterruptedException {
		System.out.println(String.format("===>当前线程: %s", Thread.currentThread().getName()));
	}
}
