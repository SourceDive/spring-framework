package mine.archive.aop.aopdemo_jdk;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.util.AopTestUtils;

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

	/**
	 * {@link AopTestUtils} 这个可以用来获取原始对象
	 */
	@Test
	public void testUtil() {
//		AopTestUtils.getTargetObject();
	}
}
