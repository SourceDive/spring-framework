package mine.archive.factorybean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FactoryBeanTests {

	@Test
	public void testFactoryBeanReturnsNullOne() {
		// 1. 创建容器并注册 NullFactoryBean
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(TestConfig.class); // 这里会自动刷新，如果下面再执行手动刷新，就会报错

		// 2. 尝试获取 Bean（预期不抛出异常）
		Object factoryBean = context.getBean("nullFactoryBean");
		System.out.println("==>");
		System.out.println(factoryBean);
	}

	@Test
	public void testFactoryBeanReturnsNull2() {
		// 1. 创建容器并注册 NullFactoryBean
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(NullFactoryBean.class); // 手工注册factorybean
		context.refresh(); // 手动执行一次刷新

		// 2. 尝试获取 Bean（预期不抛出异常）
		Object factoryBean = context.getBean("nullFactoryBean");
		System.out.println("==>");
		System.out.println(factoryBean);
	}
}