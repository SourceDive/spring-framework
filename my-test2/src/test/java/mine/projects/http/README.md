# Spring Framework HTTP 调试示例

这是一个纯Spring Framework的HTTP GET/POST示例，用于调试Spring源码中的HTTP处理流程。**不使用Spring Boot**，只使用Spring Framework核心功能。

## 文件结构

```
http/
├── SimpleController.java      # HTTP控制器
├── WebMvcConfig.java         # Spring MVC配置
├── HttpTest.java             # 测试类
├── HttpApplication.java       # 启动类
└── README.md                 # 说明文档
```

## 功能特性

### 1. HTTP端点

- **GET /api/health** - 健康检查
- **GET /api/user/{id}** - 获取用户信息
- **POST /api/user** - 创建用户

### 2. 调试功能

- 详细的请求/响应日志
- 异常处理日志
- 请求参数和响应内容打印

## 使用方法

### 方法1: 运行测试类

```bash
# 在IDE中运行 HttpTest 类
# 或者使用Maven: mvn test -Dtest=HttpTest
```

### 方法2: 启动应用

```bash
# 运行主类
java -cp target/classes mine.projects.http.HttpApplication
```

## 调试源码的关键点

### 1. 请求处理流程

- `DispatcherServlet.doDispatch()` - 请求分发
- `RequestMappingHandlerMapping.getHandlerInternal()` - 处理器映射
- `RequestMappingHandlerAdapter.handle()` - 处理器适配
- `HandlerMethod.invoke()` - 方法调用

### 2. 响应处理流程

- `HttpMessageConverter.write()` - 响应转换
- `ResponseEntity` - 响应实体
- `HttpServletResponse` - HTTP响应

### 3. 关键断点位置

```java
// DispatcherServlet.java
public void doDispatch(HttpServletRequest request, HttpServletResponse response)

// RequestMappingHandlerAdapter.java
public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)

// RequestMappingHandlerMapping.java
public HandlerMethod getHandlerInternal(HttpServletRequest request)
```

## Spring Framework vs Spring Boot

### 纯Spring Framework特点

- 使用 `@Controller` 和 `@RequestMapping`
- 手动配置 `WebMvcConfigurer`
- 使用 `AnnotationConfigApplicationContext`
- 不依赖Spring Boot的自动配置

### 关键组件

1. **DispatcherServlet** - 核心servlet
2. **RequestMappingHandlerMapping** - 请求映射处理器
3. **RequestMappingHandlerAdapter** - 请求适配器
4. **HandlerMethod** - 处理器方法

## 调试建议

1. **在IDE中设置断点**：在关键方法上设置断点
2. **查看请求参数**：检查HttpServletRequest对象
3. **跟踪响应处理**：观察HttpServletResponse对象
4. **分析异常处理**：查看异常堆栈信息

## 示例输出

```
=== GET请求处理开始 ===
接收到的用户ID: 123
返回响应: {id=123, name=用户123, email=user123@example.com, timestamp=1703123456789}
=== GET请求处理结束 ===
```

## 与Spring Boot的区别

| 特性 | Spring Framework | Spring Boot |
|------|------------------|-------------|
| 配置方式 | 手动配置 | 自动配置 |
| 启动方式 | AnnotationConfigApplicationContext | SpringApplication.run() |
| 依赖管理 | 手动添加 | 自动管理 |
| 内嵌服务器 | 需要手动配置 | 自动配置 |
