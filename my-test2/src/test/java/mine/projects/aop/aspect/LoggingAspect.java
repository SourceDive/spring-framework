package mine.projects.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author zero
 * @description todo
 * @date 2025-05-22
 */
@Aspect // 注意这个注解的使用需要在 build.gradle 文件中添加依赖
@Component
public class LoggingAspect {
	@Before("execution(* mine.projects.aop.service.UserService.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("[AOP 日志] 方法执行前: " + methodName);
	}
}
