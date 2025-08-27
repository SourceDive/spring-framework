package mine.projects.transaction.h2.propagation.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditService {
    
    private final JdbcTemplate jdbcTemplate;
    
    public AuditService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void logWithRequired(String action) {
        jdbcTemplate.update("INSERT INTO audit_log (action) VALUES (?)", action);
        // 模拟异常
        throw new RuntimeException("===>审计失败");
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logWithRequiresNew(String action) {
        jdbcTemplate.update("INSERT INTO audit_log (action) VALUES (?)", action);
        throw new RuntimeException("===>审计失败");
    }
    
    @Transactional(propagation = Propagation.NESTED)
    public void logWithNested(String action) {
        jdbcTemplate.update("INSERT INTO audit_log (action) VALUES (?)", action);
        throw new RuntimeException("===>审计失败");
    }
}