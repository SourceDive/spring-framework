package mine.archive.aopdemo_cglib_parent;

import org.springframework.stereotype.Service;

/**
 * @author zero
 * @description todo
 * @date 2025-05-27
 */
@Service
public class DemoChildService extends DemoParentService {
	@Override
	public void save() {
		System.out.println("Child Save run");
	}
}

