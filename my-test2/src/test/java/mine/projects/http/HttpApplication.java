package mine.projects.http;

import mine.projects.http.config.WebMvcConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
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

		// 创建内嵌 Tomcat 服务器
		Tomcat tomcat = new Tomcat();
		
		// 创建临时目录用于 Tomcat
		String baseDir = System.getProperty("java.io.tmpdir");
		File tempDir = new File(baseDir, "tomcat-" + System.currentTimeMillis());
		tempDir.mkdirs();

		// 设置 Tomcat 基础目录
		tomcat.setBaseDir(tempDir.getAbsolutePath());
		tomcat.setHostname("localhost");
		
		// 创建并设置 Connector（必须在 start() 之前）
		Connector connector = new Connector("HTTP/1.1");
		connector.setPort(8080);
		connector.setProperty("address", "localhost");
		tomcat.getService().addConnector(connector);
		tomcat.setConnector(connector);

		// 先创建 Tomcat Context（这样才能获取 ServletContext）
		Context context = tomcat.addContext("", tempDir.getAbsolutePath());

		// 创建 Web 应用上下文并设置 ServletContext
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
		webContext.setServletContext(context.getServletContext());
		webContext.register(WebMvcConfig.class, HttpApplication.class);
		webContext.refresh();

		// 创建并配置 DispatcherServlet
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		Tomcat.addServlet(context, "dispatcher", dispatcherServlet);
		context.addServletMappingDecoded("/", "dispatcher");

		try {
			// 启动 Tomcat 服务器
			tomcat.start();
			
			// 等待一下确保服务器完全启动
			Thread.sleep(500);
			
			// 获取实际监听的端口
			int actualPort = connector.getLocalPort();
			if (actualPort <= 0) {
				throw new IllegalStateException("服务器未能成功绑定端口，实际端口: " + actualPort);
			}
			
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

			// 保持服务器运行 - 使用 await 方法保持主线程运行
			try {
				tomcat.getServer().await();
			} catch (Exception e) {
				// 如果 await() 方法不可用，使用 Thread.sleep() 保持运行
				System.out.println("注意: await() 方法不可用，使用 Thread.sleep() 保持服务器运行");
				try {
					while (true) {
						Thread.sleep(1000);
					}
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					System.out.println("服务器被中断");
				}
			}
		} catch (LifecycleException e) {
			System.err.println("启动 Tomcat 服务器失败: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("服务器运行出错: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				// 停止服务器
				if (tomcat.getServer() != null && tomcat.getServer().getState().isAvailable()) {
					tomcat.stop();
					tomcat.destroy();
				}
			} catch (Exception e) {
				System.err.println("停止服务器时出错: " + e.getMessage());
			}
			// 清理临时目录
			try {
				deleteDirectory(tempDir);
			} catch (Exception e) {
				// 忽略清理错误
			}
		}
	}

	/**
	 * 递归删除目录
	 */
	private static void deleteDirectory(File directory) {
		if (directory.exists()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						deleteDirectory(file);
					} else {
						file.delete();
					}
				}
			}
			directory.delete();
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
