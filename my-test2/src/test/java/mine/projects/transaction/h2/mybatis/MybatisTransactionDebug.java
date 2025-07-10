package mine.projects.transaction.h2.mybatis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * @author zero
 * @description 测试事务机制(使用mybatis，不使用jdbctemplate)
 * @date 2025-07-10
 */
public class MybatisTransactionDebug {

	public static void main(String[] args) {
		// 1. 创建应用上下文（不使用Spring Boot）
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(TransactionConfig.class);

		// 2. 获取代理后的服务对象
		UserService userService = context.getBean(UserService.class);

		DataSource h2DataSource = context.getBean(DataSource.class);

		// 3. 调用事务方法（在此处设置断点）
		userService.createUser("debugUser");
	}

	// 服务接口
	public interface UserService {
		@Transactional
		void createUser(String username);

		List<String> getAllUserName();
	}

	// 服务实现
	public static class DefaultUserService implements UserService {
		@Override
		public void createUser(String username) {
			System.out.println("===>事务方法执行中...: " + username);
			// 这里可以添加数据库操作
		}

		@Override
		public List<String> getAllUserName() {
			return Collections.emptyList();
		}
	}

	// Spring 配置类
	@Configuration
	@EnableTransactionManagement // 启用事务管理
	public static class TransactionConfig {
		@Bean
		public UserService userService() {
			return new DefaultUserService();
		}

		@Bean
		public DataSource dataSource() {
			// 使用嵌入式数据库（不需要真实数据库）
			return new EmbeddedDatabaseBuilder()
					.setType(EmbeddedDatabaseType.H2)
					.build();
		}

		@Bean
		public PlatformTransactionManager transactionManager(DataSource dataSource) {
			return new DataSourceTransactionManager(dataSource);
		}
	}
}