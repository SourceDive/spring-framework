package mine.projects.http;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * 纯Spring Framework HTTP应用启动类
 * 不使用Spring Boot，只使用Spring Framework核心功能
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "mine.projects.http")
public class HttpApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		System.out.println("=== 启动Spring Framework HTTP应用 ===");
		System.out.println("使用纯Spring Framework，不依赖Spring Boot");
		System.out.println();
		System.out.println("可用的端点:");
		System.out.println("  GET  /api/health - 健康检查");
		System.out.println("  GET  /api/user/{id} - 获取用户信息");
		System.out.println("  POST /api/user - 创建用户");
		System.out.println();
		System.out.println("调试源码的关键组件:");
		System.out.println("- DispatcherServlet: 请求分发器");
		System.out.println("- RequestMappingHandlerMapping: 请求映射处理器");
		System.out.println("- RequestMappingHandlerAdapter: 请求适配器");
		System.out.println("- HandlerMethod: 处理器方法");
		System.out.println("=====================================");

		// 创建Spring应用上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(HttpApplication.class);
		context.refresh();

		System.out.println("Spring应用上下文已启动");
		System.out.println("可以运行HttpTest类进行测试");
	}

	/**
	 * 配置RequestMappingHandlerMapping
	 */
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
		mapping.setOrder(0);
		return mapping;
	}

	/**
	 * 配置RequestMappingHandlerAdapter
	 */
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
		return adapter;
	}

	/**
	 * 配置JSON消息转换器
	 */
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}

	/**
	 * 配置消息转换器列表
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJackson2HttpMessageConverter());
	}
}
