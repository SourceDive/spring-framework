package mine.archive.async_self_impl;

import mine.archive.async_self_impl.service.DemoService;
import mine.archive.async_self_impl.service.DemoServiceImpl;

/**
 * @author zero
 * @description todo
 * @date 2025-10-11
 */
public class Application {
	public static void main(String[] args) throws InterruptedException {
		DemoService service = new DemoServiceImpl();
		service.perform();
	}
}
