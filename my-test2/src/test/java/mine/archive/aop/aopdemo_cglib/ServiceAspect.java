package mine.archive.aop.aopdemo_cglib;

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
	@Before("execution(public * mine.archive.aop.aopdemo_cglib.DemoService.*(..))")
	public void beforePrint() {
		System.out.println("before run...");
	}
}
