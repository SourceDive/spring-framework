package mine.projects.http.ishandler;

import mine.projects.http.controller.SimpleTextController;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zero
 * @description 测试 isHandler
 * @date 2025-11-26
 */
public class IsHandlerTest {

	public static boolean isHandler(Class<?> beanType) {
		return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
				AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
	}

	public static void main(String[] args) {
		boolean handler = isHandler(SimpleTextController.class);
		System.out.println(handler);
	}
}
