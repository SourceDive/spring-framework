package mine.archive.aop.aopdemo_jdk;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Configuration
@ComponentScan("mine.projects.aopdemo_jdk")
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class Application {
}
