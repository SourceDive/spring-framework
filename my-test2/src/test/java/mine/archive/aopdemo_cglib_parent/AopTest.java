package mine.archive.aopdemo_cglib_parent;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description use spring framework to test aop cglib logic
 * @date 2025-05-27
 */
public class AopTest {

	@Test
	public void testAop() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

		// 获取代理对象
		DemoChildService demoChildService = context.getBean(DemoChildService.class);

		// 触发切面方法
		demoChildService.save();

		context.close();
	}
}
