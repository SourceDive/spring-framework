package mine.archive.at_bean.config;

import mine.archive.at_bean.service.MyService;
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
