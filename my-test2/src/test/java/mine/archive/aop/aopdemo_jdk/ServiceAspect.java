package mine.archive.aop.aopdemo_jdk;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Aspect
@Component
public class ServiceAspect {
	@Before("execution(public * mine.archive.aop.aopdemo_jdk.DemoService.*(..))")
	public void beforePrint() {
		System.out.println("before run...");
	}
}
