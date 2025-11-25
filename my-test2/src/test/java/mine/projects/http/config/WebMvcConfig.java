package mine.projects.http.config;

import mine.projects.http.HttpApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Spring MVC配置类
 * 纯Spring Framework配置，不使用Spring Boot
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = HttpApplication.class)
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 配置RequestMappingHandlerMapping
	 * 这是Spring MVC中处理请求映射的核心组件
	 */
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
		mapping.setOrder(0);
		return mapping;
	}

	/**
	 * 配置RequestMappingHandlerAdapter
	 * 这是Spring MVC中处理请求适配的核心组件
	 */
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
		return adapter;
	}

	/**
	 * 配置JSON消息转换器
	 * 用于处理JSON请求和响应
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}

	/**
	 * 扩展消息转换器（保留默认转换器）
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// 确保纯文本响应可用
		converters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		// 确保JSON转换可用
		converters.add(mappingJackson2HttpMessageConverter());
	}

	/**
	 * 注意：DispatcherServlet 不需要作为 Bean 配置
	 * 
	 * 原因：
	 * 1. DispatcherServlet 的构造函数需要 WebApplicationContext 作为参数
	 * 2. 在手动配置 Tomcat 的场景下，我们需要在创建 WebApplicationContext 之后
	 *    再创建 DispatcherServlet，并设置 WebApplicationContext
	 * 3. 在 @Bean 方法中无法直接获取到完整的 WebApplicationContext
	 * 
	 * 因此，DispatcherServlet 应该在 main 方法中直接创建并配置，
	 * 而不是作为 Spring Bean。
	 */
}
