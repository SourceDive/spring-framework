package mine.archive.annotation_bean.simple.config;

import mine.archive.annotation_bean.simple.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Spring 配置类
@Configuration
public class MyConfig {

	@Bean
	public MyService myService() {
		return new MyService();
	}
}
