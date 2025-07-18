package mine.projects.transaction.h2.jdbctemplate.config;

import mine.projects.transaction.h2.jdbctemplate.dao.UserDaoImpl;
import mine.projects.transaction.h2.jdbctemplate.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


// Spring 配置类
@Configuration
@EnableTransactionManagement // 启用事务管理
public class TransactionConfig {
	@Bean
	public DataSource dataSource() {
		// 使用嵌入式数据库（不需要真实数据库）
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.generateUniqueName(true) // 每次创建唯一数据库名
				.addScript("classpath:mine/projects/transaction/h2/schema.sql")
				.build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public UserDao userDao(JdbcTemplate jdbcTemplate) {
		return new UserDaoImpl(jdbcTemplate);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
