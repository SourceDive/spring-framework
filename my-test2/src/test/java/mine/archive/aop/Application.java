package mine.archive.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zero
 * @description todo
 * @date 2025-05-22
 */
@Configuration
@ComponentScan("mine.projects.aop")
@EnableAspectJAutoProxy(proxyTargetClass = true) // 强制使用 CGLIB 代理
public class Application {
}
