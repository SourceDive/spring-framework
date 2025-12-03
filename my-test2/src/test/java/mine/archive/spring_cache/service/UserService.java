package mine.archive.spring_cache.service;

import mine.archive.spring_cache.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 用户服务类 - 演示Spring Cache的核心注解
 * 这是学习Spring Cache源码的最佳入口
 */
@Service
public class UserService {

    /**
     * @Cacheable - 缓存查询结果
     * 当方法被调用时，先检查缓存，如果缓存中有数据则直接返回
     * 如果没有，则执行方法并将结果存入缓存
     * 
     * 学习要点：
     * 1. key: 缓存的键，支持SpEL表达式
     * 2. condition: 缓存条件，只有满足条件才会缓存
     * 3. unless: 排除条件，满足条件时不缓存
     */
    @Cacheable(value = "userCache", key = "#id", condition = "#id > 0")
    public String getUserById(Long id) {
        System.out.println("执行数据库查询，用户ID: " + id);
        // 模拟数据库查询耗时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新设置中断信号。
        }
        return "用户" + id + "的信息";
    }

    /**
     * @CachePut - 更新缓存
     * 无论缓存中是否存在，都会执行方法并更新缓存
     * 通常用于更新操作
     */
    @CachePut(value = "userCache", key = "#user.id")
    public String updateUser(User user) {
        System.out.println("更新用户信息: " + user);
        // 模拟数据库更新
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新设置中断信号。
        }
        return "更新后的用户" + user.getId() + "信息";
    }

    /**
     * @CacheEvict - 清除缓存
     * 执行方法后清除指定的缓存
     * 
     * 学习要点：
     * 1. allEntries: 是否清除所有缓存条目
     * 2. beforeInvocation: 是否在方法执行前清除缓存
     */
    @CacheEvict(value = "userCache", key = "#id")
    public void deleteUser(Long id) {
        System.out.println("删除用户: " + id);
        // 模拟数据库删除
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 清除所有用户缓存
     */
    @CacheEvict(value = "userCache", allEntries = true)
    public void clearAllUserCache() {
        System.out.println("清除所有用户缓存");
    }
}
