package mine.archive.spring_context_holder;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author zero
 * @description SpringContextHolder 的实现和用法，这个可以放在工具包下。
 * 使用场景：普通类中去获取容器管理的bean。
 * @date 2025-07-25
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	// 禁止实例化
	private SpringContextHolder() {
	}

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName) {
		Assert.notNull(beanName, "beanName must not be null");
		return applicationContext.getBean(beanName);
	}
}
