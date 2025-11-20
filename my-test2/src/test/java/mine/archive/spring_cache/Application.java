package mine.archive.spring_cache;

import mine.archive.spring_cache.config.CacheConfig;
import mine.archive.spring_cache.domain.User;
import mine.archive.spring_cache.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.cache.CacheManager;

/**
 * Spring Cache 学习应用主类
 * 这是学习Spring Cache源码的入口点
 * 使用纯Spring Framework配置，不依赖Spring Boot
 */
public class Application {

    public static void main(String[] args) {
        System.out.println("=== Spring Cache 学习应用启动 ===");
        
        // 创建Spring应用上下文，使用纯Spring Framework配置
        AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(CacheConfig.class);

        try {
            // 获取缓存管理器
            CacheManager cacheManager = context.getBean(CacheManager.class);
            System.out.println("缓存管理器类型: " + cacheManager.getClass().getSimpleName());
            
            // 获取用户服务
            UserService userService = context.getBean(UserService.class);
            
            // 演示缓存功能
            demonstrateCacheFeatures(userService);
            
        } finally {
            // 关闭应用上下文
            context.close();
        }
    }
    
    /**
     * 演示Spring Cache的核心功能
     */
    private static void demonstrateCacheFeatures(UserService userService) {
        System.out.println("\n=== 演示Spring Cache功能 ===");
        
        // 1. 演示 @Cacheable
        System.out.println("\n1. 演示 @Cacheable 注解:");
        long start = System.currentTimeMillis();
        String result1 = userService.getUserById(1L);
        long end = System.currentTimeMillis();
        System.out.println("第一次查询耗时: " + (end - start) + "ms, 结果: " + result1);
        
        start = System.currentTimeMillis();
        String result2 = userService.getUserById(1L);
        end = System.currentTimeMillis();
        System.out.println("第二次查询耗时: " + (end - start) + "ms, 结果: " + result2);
        System.out.println("两次结果相同: " + result1.equals(result2));
        
        // 2. 演示 @CachePut
        System.out.println("\n2. 演示 @CachePut 注解:");
        User user = new User(2L, "张三");
        String updatedResult = userService.updateUser(user);
        System.out.println("更新结果: " + updatedResult);
        
        // 3. 演示 @CacheEvict
        System.out.println("\n3. 演示 @CacheEvict 注解:");
        userService.getUserById(3L);  // 先缓存
        System.out.println("用户3已缓存");
        userService.deleteUser(3L);   // 删除并清除缓存
        System.out.println("用户3已删除并清除缓存");
        
        // 4. 演示缓存条件
        System.out.println("\n4. 演示缓存条件:");
        System.out.println("查询正数ID (应该缓存):");
        userService.getUserById(4L);
        System.out.println("查询负数ID (不应该缓存):");
        userService.getUserById(-1L);
        
        // 5. 演示清除所有缓存
        System.out.println("\n5. 演示清除所有缓存:");
        userService.getUserById(5L);
        userService.getUserById(6L);
        System.out.println("缓存了用户5和用户6");
        userService.clearAllUserCache();
        System.out.println("已清除所有用户缓存");
        
        System.out.println("\n=== 演示完成 ===");
    }
}