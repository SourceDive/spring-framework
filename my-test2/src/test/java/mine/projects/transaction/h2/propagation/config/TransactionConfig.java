package mine.projects.transaction.h2.propagation.config;

import mine.projects.transaction.h2.propagation.service.AuditService;
import mine.projects.transaction.h2.propagation.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
				.generateUniqueName(true) // 每次创建唯一数据库名
                .addScript("classpath:mine/projects/transaction/h2/propagation/schema.sql")
                .build();
    }

	// 必须的bean
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean
    public UserService userService(JdbcTemplate jdbcTemplate, AuditService auditService) {
        return new UserService(jdbcTemplate, auditService);
    }
    
    @Bean
    public AuditService auditService(JdbcTemplate jdbcTemplate) {
        return new AuditService(jdbcTemplate);
    }
}