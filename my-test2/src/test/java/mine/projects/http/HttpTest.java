package mine.projects.http;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Spring Framework HTTP测试类
 * 使用纯Spring Framework进行HTTP测试，不使用Spring Boot
 */
@SpringJUnitConfig(classes = {WebMvcConfig.class})
public class HttpTest {

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

		mockMvc.perform(get("/api/user/123"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(123))
				.andExpect(jsonPath("$.name").value("用户123"))
				.andExpect(jsonPath("$.email").value("user123@example.com"));

		System.out.println("GET请求测试完成");
	}

	/**
	 * 测试POST请求 - 创建用户
	 */
	@Test
	public void testCreateUser() throws Exception {
		System.out.println("\n=== 测试POST请求 ===");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		String userJson = "{\"name\":\"张三\",\"email\":\"zhangsan@example.com\"}";

		mockMvc.perform(post("/api/user")
						.contentType("application/json")
						.content(userJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("张三"))
				.andExpect(jsonPath("$.email").value("zhangsan@example.com"))
				.andExpect(jsonPath("$.created").value(true));

		System.out.println("POST请求测试完成");
	}

	/**
	 * 测试健康检查端点
	 */
	@Test
	public void testHealthCheck() throws Exception {
		System.out.println("\n=== 测试健康检查 ===");

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		mockMvc.perform(get("/api/health"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status").value("UP"))
				.andExpect(jsonPath("$.message").value("服务正常运行"));

		System.out.println("健康检查测试完成");
	}

	/**
	 * 手动测试方法 - 可以在IDE中直接运行
	 */
	public static void main(String[] args) {
		System.out.println("=== Spring Framework HTTP测试 ===");
		System.out.println("这个测试类使用纯Spring Framework，不依赖Spring Boot");
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
