package mine.archive.annotation_bean.lite_mode.config;

import mine.archive.annotation_bean.lite_mode.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description @Component 类中声明 @Bean 方法。
 * @date 2025-11-20
 */
@Component
public class MyComponent {

	@Bean
	public MyService myService() {
		MyService myService = new MyService();
		return myService;
	}

	@Bean
	public MyService myService1() {
		MyService service01 = myService();
		return service01;
	}
}
