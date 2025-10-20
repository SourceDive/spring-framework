package mine.archive.beanprovider;

import mine.projects.transaction.h2.jdbctemplate.dao.UserDao;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zero
 * @description 测试 beanProvider
 * @date 2025-10-20
 */
public class BeanProviderTest {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(TransactionConfig.class);
		ObjectProvider<UserDao> beanProvider = context.getBeanProvider(UserDao.class);
		System.out.println(beanProvider.getObject());
	}
}
