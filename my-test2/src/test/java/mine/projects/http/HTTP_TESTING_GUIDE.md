# HTTP测试对比指南

本文档对比两种Spring Framework HTTP测试方案，帮助你选择最适合的调试方式。

## 📊 两种方案对比

| 特性 | JSON版本 | 纯文本版本 |
|------|----------|------------|
| **控制器** | `SimpleController.java` | `SimpleTextController.java` |
| **测试类** | `HttpTest.java` | `SimpleTextTest.java` |
| **响应格式** | JSON | 纯文本 |
| **依赖** | 需要Jackson | 无外部依赖 |
| **复杂度** | 中等 | 简单 |
| **调试难度** | 中等 | 简单 |
| **适用场景** | 真实API开发 | 源码调试学习 |

## 🚀 快速开始

### 方案1：JSON版本（真实API风格）

**控制器特点：**
```java
// 返回JSON响应
public ResponseEntity<Map<String, Object>> getUser(@PathVariable("id") Long id) {
    Map<String, Object> response = new HashMap<>();
    response.put("id", id);
    response.put("name", "用户" + id);
    return new ResponseEntity<>(response, HttpStatus.OK);
}
```

**测试方式：**
```java
// 测试JSON响应
String result = mockMvc.perform(get("/api/user/123"))
    .andExpect(status().isOk())
    .andReturn()
    .getResponse()
    .getContentAsString();

assertTrue(result.contains("\"id\":123"));
```

**优点：**
- ✅ 真实的API开发体验
- ✅ JSON格式便于前端集成
- ✅ 支持复杂的请求体处理

**缺点：**
- ❌ 需要Jackson依赖
- ❌ JSON解析相对复杂
- ❌ 调试时需要理解JSON格式

### 方案2：纯文本版本（调试学习风格）

**控制器特点：**
```java
// 返回纯文本响应
public ResponseEntity<String> getUser(@PathVariable("id") Long id) {
    String response = String.format("用户ID: %d, 姓名: 用户%d", id, id);
    return new ResponseEntity<>(response, HttpStatus.OK);
}
```

**测试方式：**
```java
// 测试纯文本响应
String result = mockMvc.perform(get("/api/user/123"))
    .andExpect(status().isOk())
    .andReturn()
    .getResponse()
    .getContentAsString();

assertTrue(result.contains("用户ID: 123"));
```

**优点：**
- ✅ 无外部依赖
- ✅ 响应格式简单直观
- ✅ 调试时容易理解
- ✅ 专注于Spring Framework核心

**缺点：**
- ❌ 不是真实的API格式
- ❌ 不支持复杂的数据结构
- ❌ 前端集成需要额外处理

## 🔍 调试源码的关键断点

两种方案都可以在以下关键位置设置断点：

### 1. 请求分发
```java
// DispatcherServlet.java
public void doDispatch(HttpServletRequest request, HttpServletResponse response)
```

### 2. 处理器映射
```java
// RequestMappingHandlerMapping.java
public HandlerMethod getHandlerInternal(HttpServletRequest request)
```

### 3. 处理器适配
```java
// RequestMappingHandlerAdapter.java
public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
```

### 4. 方法调用
```java
// HandlerMethod.java
public Object invoke(Object... args)
```

## 🎯 使用建议

### 学习Spring Framework源码
**推荐：纯文本版本**
- 专注于Spring核心机制
- 减少外部依赖干扰
- 更容易理解请求处理流程

### 开发真实API
**推荐：JSON版本**
- 符合实际开发需求
- 学习JSON处理机制
- 了解消息转换器工作原理

## 🧪 测试运行

### 运行JSON版本测试
```bash
# 在IDE中运行
HttpTest.testGetUser()
HttpTest.testCreateUser()
HttpTest.testHealthCheck()
```

### 运行纯文本版本测试
```bash
# 在IDE中运行
SimpleTextTest.testGetUser()
SimpleTextTest.testCreateUser()
SimpleTextTest.testHealthCheck()
```

## 📝 调试技巧

1. **设置断点**：在关键方法上设置断点
2. **查看变量**：观察请求参数和响应内容
3. **跟踪调用栈**：理解Spring的请求处理流程
4. **对比差异**：运行两种测试，观察处理差异

## 🔧 故障排除

### 常见问题

1. **Jackson依赖错误**
   - 确保build.gradle中有Jackson依赖
   - 检查版本兼容性

2. **Servlet API错误**
   - 确保有javax.servlet-api依赖
   - 检查Spring版本兼容性

3. **测试失败**
   - 检查控制器映射路径
   - 验证请求参数格式
   - 查看控制台日志输出

## 📚 深入学习

- Spring MVC官方文档
- Spring Framework源码分析
- HTTP协议和Servlet规范
- JSON数据处理最佳实践
