package mine.projects.http.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 纯Spring Framework HTTP控制器示例
 * 用于调试Spring源码中的HTTP处理流程
 * 不使用Spring Boot，只使用Spring MVC核心功能
 */
@Controller
@RequestMapping("/api")
public class SimpleController {

	/**
	 * GET请求示例 - 获取用户信息
	 * 访问: GET /api/user/{id}
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable("id") Long id) {
		System.out.println("=== GET请求处理开始 ===");
		System.out.println("接收到的用户ID: " + id);

		Map<String, Object> response = new HashMap<>();
		response.put("id", id);
		response.put("name", "用户" + id);
		response.put("email", "user" + id + "@example.com");
		response.put("timestamp", System.currentTimeMillis());

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
	public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, Object> userData) {
		System.out.println("=== POST请求处理开始 ===");
		System.out.println("接收到的用户数据: " + userData);

		Map<String, Object> response = new HashMap<>();
		response.put("id", System.currentTimeMillis());
		response.put("name", userData.get("name"));
		response.put("email", userData.get("email"));
		response.put("created", true);
		response.put("timestamp", System.currentTimeMillis());

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
	public ResponseEntity<Map<String, String>> health() {
		System.out.println("=== 健康检查请求 ===");
		Map<String, String> response = new HashMap<>();
		response.put("status", "UP");
		response.put("message", "服务正常运行");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
