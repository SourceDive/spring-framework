package mine.projects.aopdemo;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description use spring framework to test aop logic
 * @date 2025-05-27
 */
public class AopTest {

	@Test
	public void testAop() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

		// 获取代理对象
		DemoService demoService = context.getBean(DemoService.class);

		// 触发切面方法
		demoService.save();

		// todo 为什么这里是 false 呢，明明是有代理的啊
		boolean isAopProxy = AopUtils.isAopProxy(DemoService.class);

		context.close();
	}
}
