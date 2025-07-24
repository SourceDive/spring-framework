package mine.archive.aop.aopdemo_cglib;

import org.springframework.stereotype.Service;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Service
public class DemoService {
	public void save() {
		System.out.println("Save run");
	}
}

