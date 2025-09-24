# Spring Cache 学习示例

这是一个最简单的Spring Cache学习示例，帮助你理解Spring Cache的核心概念和源码实现。
**使用纯Spring Framework配置，不依赖Spring Boot，适合在Spring Framework源码环境中学习。**

## 文件结构

```
spring_cache/
├── CacheConfig.java           # 缓存配置类
├── UserService.java          # 用户服务类（包含缓存注解）
├── CacheTest.java            # 缓存功能测试类
├── SpringCacheApplication.java # 主应用类
└── README.md                 # 说明文档
```

## 核心概念

### 1. 缓存注解

- **@Cacheable**: 缓存查询结果，如果缓存存在则直接返回
- **@CachePut**: 更新缓存，无论缓存是否存在都会执行方法并更新缓存
- **@CacheEvict**: 清除缓存，可以清除单个或所有缓存条目

### 2. 缓存配置

- **@EnableCaching**: 启用Spring Cache功能
- **CacheManager**: 缓存管理器，这里使用ConcurrentMapCacheManager作为简单实现

### 3. 缓存参数

- **value**: 缓存名称
- **key**: 缓存键，支持SpEL表达式
- **condition**: 缓存条件
- **unless**: 排除条件
- **allEntries**: 是否清除所有缓存条目

## 运行方式

### 1. 运行主应用

```bash
# 在IDE中运行 SpringCacheApplication.main() 方法
# 或者使用命令行：
java -cp your-classpath test.java.mine.projects.spring_cache.Application
```

### 2. 运行测试

```bash
# 在IDE中运行 CacheTest 类
# 或者使用Maven/Gradle运行测试
```

### 3. 纯Spring Framework配置

这个示例使用以下纯Spring Framework组件：
- `AnnotationConfigApplicationContext` - 基于注解的Spring容器
- `@Configuration` - 配置类注解
- `@EnableCaching` - 启用缓存功能
- `ConcurrentMapCacheManager` - 内存缓存管理器
- 无Spring Boot依赖，完全基于Spring Framework核心

## 学习要点

### 1. 缓存工作原理

1. **第一次调用**: 执行方法，将结果存入缓存
2. **后续调用**: 直接从缓存返回结果，不执行方法
3. **缓存更新**: 使用@CachePut更新缓存
4. **缓存清除**: 使用@CacheEvict清除缓存

### 2. 源码学习路径

1. **@EnableCaching**: 查看CachingConfigurationSelector
2. **@Cacheable**: 查看CacheableOperation
3. **CacheManager**: 查看AbstractCacheManager
4. **AOP代理**: 查看CacheInterceptor

### 3. 调试技巧

1. 在方法中添加System.out.println()观察执行流程
2. 使用断点调试缓存命中/未命中
3. 观察CacheManager中的缓存内容变化

## 扩展学习

1. **自定义CacheManager**: 实现Redis、Caffeine等缓存
2. **缓存策略**: 学习TTL、LRU等缓存策略
3. **分布式缓存**: 学习Redis集群缓存
4. **缓存监控**: 学习缓存命中率监控

## 常见问题

1. **缓存不生效**: 检查@EnableCaching是否启用
2. **缓存键冲突**: 检查key表达式是否正确
3. **缓存条件**: 检查condition和unless条件
4. **代理问题**: 确保方法调用通过Spring代理

这个示例是学习Spring Cache源码的最佳入口，建议结合Spring源码一起学习。
