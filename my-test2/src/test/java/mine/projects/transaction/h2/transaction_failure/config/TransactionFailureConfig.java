package mine.projects.transaction.h2.transaction_failure.config;

import mine.projects.transaction.h2.transaction_failure.service.UserService;
import mine.projects.transaction.h2.transaction_failure.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 事务失效测试配置类
 * 演示各种导致事务失效的场景
 */
@Configuration
@EnableTransactionManagement // 启用事务管理
public class TransactionFailureConfig {
    
    // 数据源配置
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .generateUniqueName(true) // 每次创建唯一数据库名
                .addScript("classpath:mine/projects/transaction/h2/schema.sql")
                .build();
    }

    // 事务管理器
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // JdbcTemplate
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // 用户服务
    @Bean
    public UserService userService(JdbcTemplate jdbcTemplate) {
        return new UserServiceImpl(jdbcTemplate);
    }
}
