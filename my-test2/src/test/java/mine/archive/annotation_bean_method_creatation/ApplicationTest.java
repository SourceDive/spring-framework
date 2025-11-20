package mine.archive.annotation_bean_method_creatation;

import mine.archive.annotation_bean_method_creatation.config.MyConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * @author zero
 * @description 测试 @Bean 方法之间的调用。
 * @date 25.07.27 Sun
 */
public class ApplicationTest {

	@Test
	public void testBeanMethodCreation() {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MyConfig.class);

		context.close(); // 显式关闭 context
	}

	/**
	 * 目的：测试@Bean方法产生的bean定义中beanClass属性是否存在
	 * 预期：不存在。
	 */
	@Test
	public void testBeanMethodBeanClass() {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(MyConfig.class);

		BeanDefinition aaa = context.getBeanDefinition("aaa");
		Assert.isNull(aaa.getBeanClassName(), "@Bean方法产生的bean定义中beanClass" +
				"属性应该是不存在的。");

		Class<?> aaa1 = context.getType("aaa");

		context.close(); // 显式关闭 context
	}
}