package mine.projects.annotation_value.config;

import mine.projects.annotation_value.ValueDemo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring配置类
 */
@Configuration
@ComponentScan(basePackageClasses = ValueDemo.class)
public class MyConfig {
}