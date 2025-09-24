package mine.projects.spring_cache.config;

import mine.projects.spring_cache.Application;
import mine.projects.spring_cache.service.UserService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Cache 配置类
 * 这是学习Spring Cache源码的入口配置
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
@EnableCaching  // 启用缓存功能，这是Spring Cache的核心注解
public class CacheConfig {

    /**
	 * <p>
     * 配置缓存管理器
	 * </p>
     * 使用ConcurrentMapCacheManager作为最简单的缓存实现
     * 适合学习和调试，生产环境建议使用Redis等
     */
    @Bean
    public CacheManager cacheManager() {
        // 创建基于内存的缓存管理器
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        // 可以预定义缓存名称
		List<String> input = new ArrayList<>();
		input.add("userCache");
		input.add("productCache");
        cacheManager.setCacheNames(input);
        return cacheManager;
    }
}
