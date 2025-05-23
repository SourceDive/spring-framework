package mine.projects.aop;

import mine.projects.aop.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description use spring framework to test aop logic
 * @date 2025-05-22
 */
public class AopTest {

	@Test
	public void testAop() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

		// 获取代理对象
		UserService userService = context.getBean(UserService.class);

		// 触发切面方法
		userService.addUser("Mike");

		context.close();
	}
}
