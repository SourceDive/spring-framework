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
	 * 配置DispatcherServlet
	 * 这是Spring MVC的核心servlet
	 */
	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet servlet = new DispatcherServlet();
		return servlet;
	}
}
