package mine.archive.aopdemo_cglib_parent;

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
	@Before("execution(public * mine.archive.aopdemo_cglib_parent.DemoChildService.*(..))")
	public void beforePrint() {
		System.out.println("before run...");
	}
}
