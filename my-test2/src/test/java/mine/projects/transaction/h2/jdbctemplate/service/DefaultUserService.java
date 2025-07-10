package mine.projects.transaction.h2.jdbctemplate.service;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

// 服务实现
public class DefaultUserService implements UserService {

	private final JdbcTemplate jdbcTemplate;

	private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
			"id INT AUTO_INCREMENT PRIMARY KEY, " +
			"username VARCHAR(255) UNIQUE)";

	private static final String INSERT_SQL = "INSERT INTO users (username) VALUES (?)";
	private static final String SELECT_SQL = "SELECT username FROM users WHERE username = ?";
	private static final String SELECT_ALL_SQL = "SELECT username FROM users";


	public DefaultUserService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;

		// 每次都重新创建表
		jdbcTemplate.execute(CREATE_TABLE_SQL);
	}

	@Override
	public void createUser(String username) {
		System.out.println("==>事务开始..." + username);

		// 这里可以添加数据库操作

		// 插入操作（事务方法）
		int count = jdbcTemplate.update(INSERT_SQL, username);

		System.out.println("Inserted rows: " + count);

		// 在事务中查询（同一个事务）
		String currentUser = jdbcTemplate.queryForObject(
				SELECT_SQL,
				String.class,
				username
		);

		System.out.println("==>事务结束。" + currentUser);
	}

	@Override
	public List<String> getAllUserName() {
		return jdbcTemplate.queryForList(SELECT_ALL_SQL, String.class);
	}
}
