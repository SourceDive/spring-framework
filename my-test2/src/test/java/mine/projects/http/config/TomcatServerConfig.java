package mine.projects.http.config;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

/**
 * Tomcat 服务器配置类
 * 封装内嵌 Tomcat 服务器的启动逻辑，让主类专注于 Spring MVC 流程
 */
public class TomcatServerConfig {

	/**
	 * 启动内嵌 Tomcat 服务器
	 * 
	 * @param webContext Spring Web 应用上下文
	 * @param port 服务器端口
	 * @return 启动的 Tomcat 实例
	 */
	public static Tomcat startTomcat(WebApplicationContext webContext, int port) throws Exception {
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
		connector.setPort(port);
		connector.setProperty("address", "localhost");
		tomcat.getService().addConnector(connector);
		tomcat.setConnector(connector);

		// 先创建 Tomcat Context（这样才能获取 ServletContext）
		Context context = tomcat.addContext("", tempDir.getAbsolutePath());

		// 设置 ServletContext 到 WebApplicationContext（如果还没有设置）
		if (webContext.getServletContext() == null) {
			((org.springframework.web.context.support.AnnotationConfigWebApplicationContext) webContext)
					.setServletContext(context.getServletContext());
		}

		// 刷新 Spring 上下文（必须在创建 DispatcherServlet 之前）
		if (webContext instanceof org.springframework.web.context.support.AnnotationConfigWebApplicationContext) {
			((org.springframework.web.context.support.AnnotationConfigWebApplicationContext) webContext).refresh();
		}

		// 创建 DispatcherServlet 并设置 WebApplicationContext
		DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
		Tomcat.addServlet(context, "dispatcher", dispatcherServlet);
		context.addServletMappingDecoded("/", "dispatcher");

		// 启动 Tomcat 服务器
		tomcat.start();
		
		// 等待一下确保服务器完全启动
		Thread.sleep(500);
		
		// 获取实际监听的端口
		int actualPort = connector.getLocalPort();
		if (actualPort <= 0) {
			throw new IllegalStateException("服务器未能成功绑定端口，实际端口: " + actualPort);
		}
		
		return tomcat;
	}

	/**
	 * 保持服务器运行
	 */
	public static void await(Tomcat tomcat) {
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
	}

	/**
	 * 停止服务器并清理资源
	 */
	public static void stop(Tomcat tomcat) {
		try {
			if (tomcat != null && tomcat.getServer() != null && tomcat.getServer().getState().isAvailable()) {
				tomcat.stop();
				tomcat.destroy();
			}
		} catch (Exception e) {
			System.err.println("停止服务器时出错: " + e.getMessage());
		}
	}
}

