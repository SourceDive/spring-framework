package mine.projects.http;

import mine.projects.http.config.WebMvcConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
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

		// 使用 WebApplicationContext 并注入 Mock 的 ServletContext
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.setServletContext(new MockServletContext());
		webContext.register(WebMvcConfig.class, HttpApplication.class);
		webContext.refresh();

		// 可选：创建一个 DispatcherServlet（不真正启动容器，仅用于初始化验证）
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		try {
			dispatcherServlet.init(new org.springframework.mock.web.MockServletConfig(new MockServletContext(), "dispatcher"));
		} catch (javax.servlet.ServletException e) {
			System.err.println("初始化 DispatcherServlet 失败: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Spring Web 应用上下文已启动 (MockServletContext)");
		System.out.println("可以运行各测试类进行调试");
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
