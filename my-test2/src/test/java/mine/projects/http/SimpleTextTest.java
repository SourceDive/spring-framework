package mine.projects.http;

import mine.projects.http.config.WebMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 纯文本HTTP测试类
 * 不依赖Jackson，只测试纯文本响应
 */
@SpringJUnitConfig(classes = {WebMvcConfig.class, SimpleTextController.class})
@WebAppConfiguration
public class SimpleTextTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	/**
	 * 测试GET请求 - 获取用户信息
	 */
	@Test
	public void testGetUser() throws Exception {
		System.out.println("\n=== 测试GET请求 ===");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		String result = mockMvc.perform(get("/api/user/123"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		System.out.println("响应内容: " + result);
		
		// 简单的字符串断言，不依赖任何外部库
		assertTrue(result.contains("用户ID: 123"));
		assertTrue(result.contains("姓名: 用户123"));
		assertTrue(result.contains("邮箱: user123@example.com"));

		System.out.println("GET请求测试完成");
	}

	/**
	 * 测试POST请求 - 创建用户
	 */
	@Test
	public void testCreateUser() throws Exception {
		System.out.println("\n=== 测试POST请求 ===");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		String result = mockMvc.perform(post("/api/user")
						.param("name", "张三")
						.param("email", "zhangsan@example.com"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		System.out.println("响应内容: " + result);
		
		// 简单的字符串断言，不依赖任何外部库
		assertTrue(result.contains("创建用户成功"));
		assertTrue(result.contains("姓名=张三"));
		assertTrue(result.contains("邮箱=zhangsan@example.com"));

		System.out.println("POST请求测试完成");
	}

	/**
	 * 测试健康检查端点
	 */
	@Test
	public void testHealthCheck() throws Exception {
		System.out.println("\n=== 测试健康检查 ===");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		String result = mockMvc.perform(get("/api/health"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		System.out.println("响应内容: " + result);
		
		// 简单的字符串断言，不依赖任何外部库
		assertTrue(result.contains("服务状态: UP"));
		assertTrue(result.contains("服务正常运行"));

		System.out.println("健康检查测试完成");
	}

	/**
	 * 手动测试方法 - 可以在IDE中直接运行
	 */
	public static void main(String[] args) {
		System.out.println("=== Spring Framework 纯文本HTTP测试 ===");
		System.out.println("这个测试类不依赖Jackson，只使用纯文本响应");
		System.out.println();
		System.out.println("可用的测试方法:");
		System.out.println("1. testGetUser() - 测试GET请求");
		System.out.println("2. testCreateUser() - 测试POST请求");
		System.out.println("3. testHealthCheck() - 测试健康检查");
		System.out.println();
		System.out.println("调试源码的关键断点:");
		System.out.println("- DispatcherServlet.doDispatch()");
		System.out.println("- RequestMappingHandlerMapping.getHandlerInternal()");
		System.out.println("- RequestMappingHandlerAdapter.handle()");
		System.out.println("- HandlerMethod.invoke()");
		System.out.println();
		System.out.println("=====================================");
	}
}
