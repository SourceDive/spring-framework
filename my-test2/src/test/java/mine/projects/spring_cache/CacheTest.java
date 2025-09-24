package mine.projects.spring_cache;

import mine.projects.spring_cache.config.CacheConfig;
import mine.projects.spring_cache.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Cache 功能测试类
 * 这个测试类帮助你理解Spring Cache的工作原理
 * 使用纯Spring Framework配置，不依赖Spring Boot
 */
public class CacheTest {

    private AnnotationConfigApplicationContext context;
    private UserService userService;
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        // 创建Spring应用上下文，使用纯Spring Framework配置
        context = new AnnotationConfigApplicationContext();
        context.register(CacheConfig.class, UserService.class);
        context.refresh();
        
        // 获取Bean
        userService = context.getBean(UserService.class);
        cacheManager = context.getBean(CacheManager.class);
    }

    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    /**
     * 测试 @Cacheable 注解
     * 验证缓存是否生效
     */
    @Test
    public void testCacheable() {
        System.out.println("=== 测试 @Cacheable 注解 ===");
        
        // 第一次调用，应该执行方法并缓存结果
        long startTime = System.currentTimeMillis();
        String result1 = userService.getUserById(1L);
        long endTime = System.currentTimeMillis();
        System.out.println("第一次调用耗时: " + (endTime - startTime) + "ms");
        System.out.println("结果: " + result1);
        
        // 第二次调用，应该从缓存获取，不执行方法
        startTime = System.currentTimeMillis();
        String result2 = userService.getUserById(1L);
        endTime = System.currentTimeMillis();
        System.out.println("第二次调用耗时: " + (endTime - startTime) + "ms");
        System.out.println("结果: " + result2);
        
        // 验证结果相同
        assertEquals(result1, result2);
        
        // 验证缓存中存在数据
        assertNotNull(cacheManager.getCache("userCache").get(1L));
    }

    /**
     * 测试 @CachePut 注解
     * 验证缓存更新功能
     */
    @Test
    public void testCachePut() {
        System.out.println("=== 测试 @CachePut 注解 ===");
        
        // 先缓存一个用户
        userService.getUserById(2L);
        
        // 更新用户信息
        UserService.User user = new UserService.User(2L, "新用户名");
        String updatedResult = userService.updateUser(user);
        System.out.println("更新结果: " + updatedResult);
        
        // 验证缓存已被更新
        String cachedResult = userService.getUserById(2L);
        assertEquals(updatedResult, cachedResult);
    }

    /**
     * 测试 @CacheEvict 注解
     * 验证缓存清除功能
     */
    @Test
    public void testCacheEvict() {
        System.out.println("=== 测试 @CacheEvict 注解 ===");
        
        // 先缓存一个用户
        userService.getUserById(3L);
        
        // 验证缓存存在
        assertNotNull(cacheManager.getCache("userCache").get(3L));
        
        // 删除用户，应该清除缓存
        userService.deleteUser(3L);
        
        // 验证缓存已被清除
        assertNull(cacheManager.getCache("userCache").get(3L));
    }

    /**
     * 测试缓存条件
     * 验证 condition 参数的作用
     */
    @Test
    public void testCacheCondition() {
        System.out.println("=== 测试缓存条件 ===");
        
        // 测试正数ID，应该被缓存
        userService.getUserById(4L);
        assertNotNull(cacheManager.getCache("userCache").get(4L));
        
        // 测试负数ID，不应该被缓存（condition = "#id > 0"）
        userService.getUserById(-1L);
        assertNull(cacheManager.getCache("userCache").get(-1L));
    }

    /**
     * 测试清除所有缓存
     */
    @Test
    public void testClearAllCache() {
        System.out.println("=== 测试清除所有缓存 ===");
        
        // 缓存多个用户
        userService.getUserById(5L);
        userService.getUserById(6L);
        userService.getUserById(7L);
        
        // 验证缓存存在
        assertNotNull(cacheManager.getCache("userCache").get(5L));
        assertNotNull(cacheManager.getCache("userCache").get(6L));
        assertNotNull(cacheManager.getCache("userCache").get(7L));
        
        // 清除所有缓存
        userService.clearAllUserCache();
        
        // 验证所有缓存都被清除
        assertNull(cacheManager.getCache("userCache").get(5L));
        assertNull(cacheManager.getCache("userCache").get(6L));
        assertNull(cacheManager.getCache("userCache").get(7L));
    }

    /**
     * 打印缓存状态，用于调试
     */
    @Test
    public void printCacheStatus() {
        System.out.println("=== 缓存状态 ===");
        
        if (cacheManager instanceof ConcurrentMapCacheManager) {
            ConcurrentMapCacheManager concurrentMapCacheManager = (ConcurrentMapCacheManager) cacheManager;
            System.out.println("缓存管理器类型: " + cacheManager.getClass().getSimpleName());
            System.out.println("缓存名称: " + concurrentMapCacheManager.getCacheNames());
        }
        
        // 缓存一些数据
        userService.getUserById(8L);
        userService.getUserById(9L);
        
        // 打印缓存内容
        System.out.println("userCache 缓存内容:");
        @SuppressWarnings("unchecked")
        java.util.Map<Object, Object> nativeCache = (java.util.Map<Object, Object>) cacheManager.getCache("userCache").getNativeCache();
        nativeCache.forEach((key, value) -> {
            System.out.println("  Key: " + key + ", Value: " + value);
        });
    }
}