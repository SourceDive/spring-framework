package mine.projects.getbean_name_type;

import mine.projects.getbean_name_type.config.TestConfig;
import mine.projects.getbean_name_type.service.PaymentResult;
import mine.projects.getbean_name_type.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author zero
 * @description getBean(name, type) 方法测试
 * @date 2025-07-27
 */
@ExtendWith(SpringExtension.class) // 集成spring基础设施
@ContextConfiguration(classes = TestConfig.class)
public class PerformanceTest {
	private static final Logger logger = LoggerFactory.getLogger(PerformanceTest.class);

	private PaymentService paymentService;

	@Autowired
	private ApplicationContext context;

	// 这里可以缓存引用，避免在循环中重复创建
	@BeforeEach
	public void setUp() {
		// ✅ 一次性获取并缓存
		paymentService = context.getBean("paymentService", PaymentService.class);
	}

	@Test
	public void testThroughput() {
		for (int i = 0; i < 1_000; i++) {
			PaymentResult paymentResult = paymentService.processPayment();
			logger.info(paymentResult.getResult());
		}
	}
}
