package mine.archive.aop.aopdemo_jdk;

import org.springframework.stereotype.Service;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Service
public class DemoService implements DemoInterface {
	public void save() {
		System.out.println("Save run");
	}
}

