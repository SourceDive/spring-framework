package mine.projects.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 纯文本HTTP控制器示例
 * 不依赖Jackson，只返回纯文本响应
 * 用于调试Spring源码中的HTTP处理流程
 */
@Controller
@RequestMapping("/text-api")
public class SimpleTextController {

	/**
	 * GET请求示例 - 获取用户信息
	 * 访问: GET /api/user/{id}
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getUser(@PathVariable("id") Long id) {
		System.out.println("=== GET请求处理开始 ===");
		System.out.println("接收到的用户ID: " + id);

		String response = String.format("用户ID: %d, 姓名: 用户%d, 邮箱: user%d@example.com, 时间戳: %d", 
			id, id, id, System.currentTimeMillis());

		System.out.println("返回响应: " + response);
		System.out.println("=== GET请求处理结束 ===");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * POST请求示例 - 创建用户
	 * 访问: POST /api/user
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createUser(@RequestParam("name") String name, 
											@RequestParam("email") String email) {
		System.out.println("=== POST请求处理开始 ===");
		System.out.println("接收到的用户数据: name=" + name + ", email=" + email);

		String response = String.format("创建用户成功: ID=%d, 姓名=%s, 邮箱=%s, 时间戳=%d", 
			System.currentTimeMillis(), name, email, System.currentTimeMillis());

		System.out.println("返回响应: " + response);
		System.out.println("=== POST请求处理结束 ===");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * 简单的健康检查端点
	 * 访问: GET /api/health
	 */
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> health() {
		System.out.println("=== 健康检查请求 ===");
		String response = "服务状态: UP, 消息: 服务正常运行";
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
