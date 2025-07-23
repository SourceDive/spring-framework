package mine.archive.at_bean_transactional.config;

import mine.archive.at_bean_transactional.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


// Spring 配置类
@Configuration
@EnableTransactionManagement
public class MyConfig {

	@Bean
	public MyService myService() {
		return new MyService();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager();
	}
}
