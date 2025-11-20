package mine.archive.annotation_bean.transactional.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zero
 * @description todo
 * @date 2025-07-23
 */
public class MyService {
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void execute() {
		System.out.println("===>执行了 MyService");
	}
}
