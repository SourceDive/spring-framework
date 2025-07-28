package mine.archive.getbean_name_type.config;

import mine.archive.getbean_name_type.service.PaymentServiceImpl;
import mine.archive.getbean_name_type.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
	@Bean(name = "paymentService")
	public PaymentService paymentService() {
		return new PaymentServiceImpl(); // 测试专用实现
	}
}
