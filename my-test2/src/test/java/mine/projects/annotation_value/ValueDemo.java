package mine.projects.annotation_value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description 最简单的@Value注解使用示例，用于学习Spring源码流程
 * 
 * 源码阅读入口：
 * 1. @Value注解定义：spring-beans/src/main/java/org/springframework/beans/factory/annotation/Value.java
 * 2. 处理类：spring-beans/src/main/java/org/springframework/beans/factory/annotation/AutowiredAnnotationBeanPostProcessor.java
 * 3. 关键方法：AutowiredAnnotationBeanPostProcessor#postProcessProperties()
 * 4. 值解析：通过BeanExpressionResolver解析SpEL表达式和占位符
 * 
 * @date 25.11.19 Wed
 */
public class ValueDemo {

	/**
	 * 使用@Value注解的Bean类
	 * 这是最简单的示例，用于追踪@Value的注入流程
	 */
	@Component
	static class ValueBean {

		/**
		 * 最简单的@Value使用：直接注入字符串值
		 * 断点位置：AutowiredAnnotationBeanPostProcessor#postProcessProperties()
		 */
		@Value("Hello Spring")
		private String simpleValue;

		/**
		 * 使用占位符注入值：${property.name:defaultValue}
		 * 断点位置：查看PropertySourcesPlaceholderConfigurer如何处理占位符
		 */
		@Value("${app.name:MyApp}")
		private String appName;

		/**
		 * 使用SpEL表达式注入值：#{expression}
		 * 断点位置：查看BeanExpressionResolver如何解析SpEL
		 */
		@Value("#{100 + 200}")
		private int calculatedValue;

		public String getSimpleValue() {
			return simpleValue;
		}

		public String getAppName() {
			return appName;
		}

		public int getCalculatedValue() {
			return calculatedValue;
		}
	}

	/**
	 * Spring配置类
	 */
	@Configuration
	@ComponentScan(basePackageClasses = ValueDemo.class)
	static class AppConfig {
	}

	public static void main(String[] args) {
		System.out.println("========== @Value注解源码学习入口 ==========\n");

		// 创建Spring应用上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// 获取Bean实例
		ValueBean bean = context.getBean(ValueBean.class);

		// 打印注入的值
		System.out.println("1. 直接字符串注入: " + bean.getSimpleValue());
		System.out.println("2. 占位符注入: " + bean.getAppName());
		System.out.println("3. SpEL表达式注入: " + bean.getCalculatedValue());

		System.out.println("\n========== 源码阅读建议 ==========");
		System.out.println("在以下位置设置断点进行调试：");
		System.out.println("1. AutowiredAnnotationBeanPostProcessor.postProcessProperties()");
		System.out.println("2. AutowiredAnnotationBeanPostProcessor.buildAutowiringMetadata()");
		System.out.println("3. AutowiredFieldElement.inject()");
		System.out.println("4. DefaultListableBeanFactory.resolveDependency()");
		System.out.println("5. BeanExpressionResolver.resolveValue()");

		context.close();
	}
}

