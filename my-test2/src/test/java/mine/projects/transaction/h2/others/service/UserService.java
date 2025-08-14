package mine.projects.transaction.h2.others.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private final JdbcTemplate jdbcTemplate;
    private final AuditService auditService;
    
    public UserService(JdbcTemplate jdbcTemplate, AuditService auditService) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditService = auditService;
    }
    
    // 测试REQUIRED传播
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUserWithRequired() {
        jdbcTemplate.update("INSERT INTO users (id, name) VALUES (1, 'John')");
        auditService.logWithRequired("User created"); // 里面会抛异常
    }
    
    // 测试REQUIRES_NEW传播
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUserWithRequiresNew() {
        jdbcTemplate.update("INSERT INTO users (id, name) VALUES (2, 'Alice')");
        try {
            auditService.logWithRequiresNew("User created");
        } catch (Exception e) {
            // 处理异常但继续主事务
        }
    }
    
    // 测试NESTED传播
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUserWithNested() {
        jdbcTemplate.update("INSERT INTO users (id, name) VALUES (3, 'Bob')");
        try {
            auditService.logWithNested("User created");
        } catch (Exception e) {
            // 处理异常但继续主事务
        }
    }
}