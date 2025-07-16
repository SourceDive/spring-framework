package mine.projects.transaction.h2.jdbctemplate.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 服务接口
public interface UserDao {
	@Transactional(propagation = Propagation.REQUIRED)
	void createUser(String username);

	List<String> getAllUserName();
}
