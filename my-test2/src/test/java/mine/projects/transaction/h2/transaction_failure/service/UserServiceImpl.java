package mine.projects.transaction.h2.transaction_failure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务实现类
 * 演示各种事务失效的场景
 */
public class UserServiceImpl implements UserService {
    
    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "username VARCHAR(255) UNIQUE)";
    
    private static final String INSERT_SQL = "INSERT INTO users (username) VALUES (?)";
    private static final String SELECT_ALL_SQL = "SELECT username FROM users";
    
    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        // 每次都重新创建表
        jdbcTemplate.execute(CREATE_TABLE_SQL);
    }
    
    @Override
    @Transactional
    public void createUserSuccess(String username) {
        logger.info("===> 正常事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 正常事务方法结束: {}", username);
    }
    
    @Override
    @Transactional
    public void createUserWithException(String username) {
        logger.info("===> 异常事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 插入用户后，准备抛出异常");
        throw new RuntimeException("模拟异常，应该回滚");
    }
    
    @Override
    @Transactional
    public void createUserPrivateMethod(String username) {
        logger.info("===> 私有方法事务开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 私有方法事务结束: {}", username);
    }
    
    @Override
    public void callTransactionalMethodInternally(String username) {
        logger.info("===> 内部调用事务方法开始: {}", username);
        // 同一个类内部调用，事务失效
        this.createUserSuccess(username + "_internal");
        logger.info("===> 内部调用事务方法结束: {}", username);
    }
    
    @Override
    @Transactional
    public void createUserWithCaughtException(String username) {
        logger.info("===> 捕获异常事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        try {
            logger.info("===> 准备抛出异常");
            throw new RuntimeException("模拟异常");
        } catch (Exception e) {
            logger.info("===> 捕获异常，事务不会回滚: {}", e.getMessage());
        }
        logger.info("===> 捕获异常事务方法结束: {}", username);
    }
    
    @Override
    @Transactional
    public void createUserWithCheckedException(String username) throws Exception {
        logger.info("===> 检查异常事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 准备抛出检查异常");
        throw new Exception("检查异常，默认不会回滚");
    }
    
    @Override
    @Transactional
    public static void createUserStaticMethod(String username) {
        logger.info("===> 静态方法事务开始: {}", username);
        // 静态方法无法被代理，事务失效
        logger.info("===> 静态方法事务结束: {}", username);
    }
    
    @Override
    @Transactional
    public final void createUserFinalMethod(String username) {
        logger.info("===> final方法事务开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> final方法事务结束: {}", username);
    }
    
    @Override
    public void callTransactionalMethodWithoutAnnotation(String username) {
        logger.info("===> 无注解调用事务方法开始: {}", username);
        // 没有@Transactional注解的方法调用事务方法
        this.createUserSuccess(username + "_no_annotation");
        logger.info("===> 无注解调用事务方法结束: {}", username);
    }
    
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createUserWithNotSupported(String username) {
        logger.info("===> NOT_SUPPORTED事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> NOT_SUPPORTED事务方法结束: {}", username);
    }
    
    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void createUserWithNever(String username) {
        logger.info("===> NEVER事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> NEVER事务方法结束: {}", username);
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void createUserWithSupports(String username) {
        logger.info("===> SUPPORTS事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> SUPPORTS事务方法结束: {}", username);
    }
    
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createUserWithMandatory(String username) {
        logger.info("===> MANDATORY事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> MANDATORY事务方法结束: {}", username);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createUserWithRequiresNew(String username) {
        logger.info("===> REQUIRES_NEW事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> REQUIRES_NEW事务方法结束: {}", username);
    }
    
    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void createUserWithNested(String username) {
        logger.info("===> NESTED事务方法开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> NESTED事务方法结束: {}", username);
    }
    
    @Override
    public List<String> getAllUserNames() {
        return jdbcTemplate.queryForList(SELECT_ALL_SQL, String.class);
    }
}
