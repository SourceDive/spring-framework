package mine.projects.transaction.h2.jdbctemplate.service;

import java.util.Collections;
import java.util.List;

// 服务实现
public class DefaultUserService implements UserService {
	@Override
	public void createUser(String username) {
		System.out.println("==>事务开始...");
		// 这里可以添加数据库操作

		System.out.println("==>事务结束。");
	}

	@Override
	public List<String> getAllUserName() {
		return Collections.emptyList();
	}
}
