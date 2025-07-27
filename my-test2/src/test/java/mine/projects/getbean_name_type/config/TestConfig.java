package mine.projects.getbean_name_type.config;

import mine.projects.getbean_name_type.service.PaymentServiceImpl;
import mine.projects.getbean_name_type.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
	@Bean(name = "paymentService")
	public PaymentService paymentService() {
		return new PaymentServiceImpl(); // 测试专用实现
	}
}
