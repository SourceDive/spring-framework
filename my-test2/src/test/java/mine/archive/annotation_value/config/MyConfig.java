package mine.archive.annotation_value.config;

import mine.archive.annotation_value.ValueDemo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring配置类
 */
@Configuration
@ComponentScan(basePackageClasses = ValueDemo.class)
public class MyConfig {
}