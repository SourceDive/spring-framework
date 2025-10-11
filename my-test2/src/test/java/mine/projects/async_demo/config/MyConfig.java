package mine.projects.async_demo.config;

import mine.projects.async_demo.AsyncDemoApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步配置类
 * 启用异步功能并配置线程池
 */
@Configuration
@ComponentScan(basePackageClasses = AsyncDemoApplication.class)
@EnableAsync
public class MyConfig {

    /**
     * 配置异步任务执行器
     */
    @Bean(name = "myAsyncExecutor")
    public Executor myAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("My-Async-");
        executor.initialize();
        return executor;
    }
}
