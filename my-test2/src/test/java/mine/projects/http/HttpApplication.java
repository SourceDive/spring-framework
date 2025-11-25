package mine.projects.http;

import mine.projects.http.config.TomcatServerConfig;
import mine.projects.http.config.WebMvcConfig;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
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

		Tomcat tomcat = null;
		try {
			// ========== Spring MVC 核心流程 ==========
			// 1. 创建 Web 应用上下文
			AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
			webContext.register(WebMvcConfig.class, HttpApplication.class);
			
			// 2. 启动 Tomcat 服务器（内部会设置 ServletContext 并 refresh 上下文）
			//    注意：startTomcat 内部会完成以下步骤：
			//    - 创建 Tomcat Context 获取 ServletContext
			//    - 设置 ServletContext 到 WebApplicationContext
			//    - refresh WebApplicationContext
			//    - 创建并注册 DispatcherServlet
			//    - 启动 Tomcat 服务器
			tomcat = TomcatServerConfig.startTomcat(webContext, 8080);
			
			// ========== 服务器信息输出 ==========
			int actualPort = tomcat.getConnector().getLocalPort();
			System.out.println();
			System.out.println("=====================================");
			System.out.println("服务器已启动！");
			System.out.println("访问地址: http://localhost:" + actualPort);
			System.out.println();
			System.out.println("示例请求:");
			System.out.println("  curl http://localhost:" + actualPort + "/api/health");
			System.out.println("  curl http://localhost:" + actualPort + "/api/user/1");
			System.out.println("  curl -X POST http://localhost:" + actualPort + "/api/user -H 'Content-Type: application/json' -d '{\"name\":\"张三\",\"email\":\"zhangsan@example.com\"}'");
			System.out.println();
			System.out.println("按 Ctrl+C 停止服务器");
			System.out.println("=====================================");

			// ========== 保持服务器运行 ==========
			TomcatServerConfig.await(tomcat);
		} catch (Exception e) {
			System.err.println("服务器运行出错: " + e.getMessage());
			e.printStackTrace();
		} finally {
			TomcatServerConfig.stop(tomcat);
		}
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
