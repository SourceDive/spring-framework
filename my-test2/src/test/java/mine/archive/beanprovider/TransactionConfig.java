package mine.archive.beanprovider;

import mine.projects.transaction.h2.jdbctemplate.dao.UserDao;
import mine.projects.transaction.h2.jdbctemplate.dao.UserDaoImpl;
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
// 里面包含的bean都是必须要包含的bean。
@Configuration
@EnableTransactionManagement // 启用事务管理
public class TransactionConfig {
	// 必须的bean
	@Bean
	public DataSource dataSource() { // 这个应该是最先初始化出来的，因为它不依赖别人。
		// 使用嵌入式数据库（不需要真实数据库）
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.generateUniqueName(true) // 每次创建唯一数据库名
				.addScript("classpath:mine/projects/transaction/h2/schema.sql")
				.build();
	}

	// 必须的bean
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	// 必须的bean
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
