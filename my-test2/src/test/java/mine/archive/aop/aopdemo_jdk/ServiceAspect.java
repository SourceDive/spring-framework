package mine.archive.aop.aopdemo_jdk;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Aspect
@Component
public class ServiceAspect {
	@Pointcut("execution(* mine.archive.aop.aopdemo_jdk.DemoService.*(..))")
	public void pointCut() {}

	@Before("pointCut()")
	public void beforePrint() {
		System.out.println("before run...");
	}
}
