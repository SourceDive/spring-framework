package mine.archive.annotation_value.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 使用@Value注解的Bean类
 * 这是最简单的示例，用于追踪@Value的注入流程
 */
@Component
public class MyService {

	/**
	 * 最简单的@Value使用：直接注入字符串值
	 */
	@Value("Hello Spring")
	private String simpleValue;

	/**
	 * 使用占位符注入值：${property.name:defaultValue}
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