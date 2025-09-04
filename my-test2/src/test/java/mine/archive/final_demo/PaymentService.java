package mine.archive.final_demo;

/**
 * @author zero
 * @description final字段在构造函数中初始化。
 * @date 2025-07-27
 */
public class PaymentService {
	private final PaymentGateway paymentGateway;

	public PaymentService(PaymentGateway paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public static void main(String[] args) {
		PaymentService paymentService = new PaymentService(new PaymentGateway());
	}
}
