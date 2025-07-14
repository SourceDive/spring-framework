package mine.projects.transaction.h2.jdbctemplate.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 服务接口
public interface UserDao {
	@Transactional
	void createUser(String username);

	List<String> getAllUserName();
}
