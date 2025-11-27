package mine.projects.http.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 纯文本HTTP控制器示例
 * 不依赖Jackson，只返回纯文本响应
 * 用于调试Spring源码中的HTTP处理流程
 */
@RestController
@RequestMapping("/text-api")
public class SimpleTextController {

	/**
	 * GET请求示例 - 获取用户信息
	 * 访问: GET /api/user/{id}
	 */
	@GetMapping(value = "/user/{id}")
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
	 * 支持JSON格式: POST /text-api/user
	 * 支持表单格式: POST /text-api/user (Content-Type: application/x-www-form-urlencoded)
	 */
	@PostMapping(value = "/user")
	public ResponseEntity<String> createUser(@RequestBody(required = false) Map<String, String> jsonData,
											 @RequestParam(value = "name", required = false) String name,
											 @RequestParam(value = "email", required = false) String email) {
		System.out.println("=== POST请求处理开始 ===");

		// 优先从JSON body中获取数据，如果没有则从请求参数中获取（兼容表单提交）
		String userName = null;
		String userEmail = null;

		if (jsonData != null && !jsonData.isEmpty()) {
			// JSON格式请求
			userName = jsonData.get("name");
			userEmail = jsonData.get("email");
			System.out.println("接收到的JSON数据: " + jsonData);
		} else {
			// 表单格式请求
			userName = name;
			userEmail = email;
			System.out.println("接收到的表单数据: name=" + name + ", email=" + email);
		}

		if (userName == null || userEmail == null) {
			String errorMsg = "缺少必要参数: name 或 email";
			System.out.println("错误: " + errorMsg);
			return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
		}

		String response = String.format("创建用户成功: ID=%d, 姓名=%s, 邮箱=%s, 时间戳=%d",
				System.currentTimeMillis(), userName, userEmail, System.currentTimeMillis());

		System.out.println("返回响应: " + response);
		System.out.println("=== POST请求处理结束 ===");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * 简单的健康检查端点
	 * 访问: GET /api/health
	 */
	@GetMapping(value = "/health")
	public ResponseEntity<String> health() {
		System.out.println("=== 健康检查请求 ===");
		String response = "服务状态: UP, 消息: 服务正常运行";
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
