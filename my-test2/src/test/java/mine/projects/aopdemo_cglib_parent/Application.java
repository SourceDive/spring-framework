package mine.projects.aopdemo_cglib_parent;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Configuration
@ComponentScan("mine.projects.aopdemo_cglib_parent")
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class Application {
}
