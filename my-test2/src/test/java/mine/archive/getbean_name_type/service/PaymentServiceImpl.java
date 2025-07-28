package mine.archive.getbean_name_type.service;


/**
 * @author zero
 * @description todo
 * @date 2025-07-27
 */
public class PaymentServiceImpl implements PaymentService {

	@Override
	public PaymentResult processPayment() {
		return new PaymentResult("===> MockPaymentService processPayment");
	}
}
