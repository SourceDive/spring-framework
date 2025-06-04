package mine.projects.aopdemo_jdk;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description use spring framework to test aop jdk proxy logic
 * @date 2025-06-04
 */
public class AopTest {

	@Test
	public void testAop() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

		// 获取代理对象(注意，这里jdk代理获取bean必须要用接口的类型!)
		DemoInterface demoInterface = context.getBean(DemoInterface.class);

		// 触发切面方法
		demoInterface.save();

		context.close();
	}
}
