package mine.projects.getbean_name_type;

import mine.projects.getbean_name_type.config.TestConfig;
import mine.projects.getbean_name_type.service.PaymentResult;
import mine.projects.getbean_name_type.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * getBean(name, type) 方法测试
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class) // 设置容器只需要加载配置文件中的 bean
public class PaymentServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceTest.class);

	@Autowired
	private ApplicationContext context; // todo 注入 context 的过程是怎样的？

	@Test
	public void testPaymentProcessing() {
		// ✅ 类型安全获取
		PaymentService service =
				context.getBean("paymentService", PaymentService.class);

		PaymentResult result = service.processPayment();
		logger.info(result.getResult());
	}
}