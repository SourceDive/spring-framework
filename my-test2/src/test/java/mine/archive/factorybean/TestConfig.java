package mine.archive.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
	@Bean
	public FactoryBean<Object> nullFactoryBean() {
		return new NullFactoryBean();
	}
}