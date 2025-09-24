package mine.projects.circular_dependency_aop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author zero
 * @description 使其生成代理对象
 * @date 25.09.23 Tue
 */
@Component
public class Cat {
	@Autowired
    Person person;

	@Transactional
	public void execute() {
		System.out.println("Cat.execute() 被调用");
		System.out.println("当前事务是否激活: " + TransactionSynchronizationManager.isActualTransactionActive());
		System.out.println("当前事务名称: " + TransactionSynchronizationManager.getCurrentTransactionName());
	}
}
