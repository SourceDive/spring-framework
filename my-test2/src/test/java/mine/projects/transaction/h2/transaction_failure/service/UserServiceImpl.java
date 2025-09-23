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
    public void callPrivateMethod(String username) {
        logger.info("===> 调用私有方法开始: {}", username);
        // 调用真正的私有方法
        createUserPrivateMethodReal(username);
        logger.info("===> 调用私有方法结束: {}", username);
    }
    
    /**
     * 真正的私有方法，@Transactional注解会失效
     */
    @Transactional
    private void createUserPrivateMethodReal(String username) {
        logger.info("===> 私有方法事务开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 私有方法事务结束: {}", username);
    }
    
    @Override
    public void callPackageMethod(String username) {
        logger.info("===> 调用包级别方法开始: {}", username);
        // 调用包级别方法
        createUserPackageMethod(username);
        logger.info("===> 调用包级别方法结束: {}", username);
    }
    
    /**
     * 包级别访问的方法，@Transactional注解会失效
     */
    @Transactional
    void createUserPackageMethod(String username) {
        logger.info("===> 包级别方法事务开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 包级别方法事务结束: {}", username);
    }
    
    @Override
    public void callPrivateMethodWithException(String username) {
        logger.info("===> 调用私有方法（带异常）开始: {}", username);
        try {
            // 调用私有方法，里面会抛出异常
            createUserPrivateMethodWithExceptionReal(username);
        } catch (RuntimeException e) {
            logger.info("===> 捕获私有方法异常: {}", e.getMessage());
        }
        logger.info("===> 调用私有方法（带异常）结束: {}", username);
    }
    
    /**
     * 私有方法，带异常，@Transactional注解会失效
     */
    @Transactional
    private void createUserPrivateMethodWithExceptionReal(String username) {
        logger.info("===> 私有方法事务开始: {}", username);
        jdbcTemplate.update(INSERT_SQL, username);
        logger.info("===> 插入用户后，准备抛出异常");
        throw new RuntimeException("私有方法中的异常，由于事务失效，不会回滚");
    }
    
    @Override
    public void callTransactionalMethodInternally(String username) {
        logger.info("===> 内部调用事务方法开始: {}", username);
        // 同一个类内部调用，事务失效
		try {
			this.createUserWithException(username + "_internal");
		} catch (RuntimeException e) {
			logger.info("===> 捕获内部方法异常: {}", e.getMessage());
		}
        logger.info("===> 内部调用事务方法结束: {}", username);
    }
    
    @Override
    public void verifyThisReference(String username) {
        logger.info("===> 验证this引用开始: {}", username);
        
        // 打印this的类名
        logger.info("===> this.getClass().getName(): {}", this.getClass().getName());
        logger.info("===> this.getClass().getSimpleName(): {}", this.getClass().getSimpleName());
        
        // 检查是否是代理对象
        boolean isProxy = this.getClass().getName().contains("$");
        logger.info("===> 是否是代理对象: {}", isProxy);
        
        // 检查是否有@Transactional注解
        try {
            java.lang.reflect.Method method = this.getClass().getMethod("createUserWithException", String.class);
            boolean hasTransactional = method.isAnnotationPresent(org.springframework.transaction.annotation.Transactional.class);
            logger.info("===> createUserWithException方法是否有@Transactional注解: {}", hasTransactional);
        } catch (NoSuchMethodException e) {
            logger.info("===> 无法找到createUserWithException方法");
        }
        
        // 内部调用事务方法
        try {
            this.createUserWithException(username + "_this_ref");
        } catch (RuntimeException e) {
            logger.info("===> 捕获this调用异常: {}", e.getMessage());
        }
        
        logger.info("===> 验证this引用结束: {}", username);
    }
    
    @Override
    public void compareProxyVsTargetCall(String username) {
        logger.info("===> 对比代理对象调用和目标对象调用开始: {}", username);
        
        // 注意：在这个方法内部，this调用仍然是目标对象调用
        // 真正的代理对象调用是在测试类中通过userService调用的
        
        logger.info("===> 当前方法中的this调用（目标对象调用）");
        try {
            this.createUserWithException(username + "_target_call");
        } catch (RuntimeException e) {
            logger.info("===> 目标对象调用异常: {}", e.getMessage());
        }
        
        logger.info("===> 对比说明：");
        logger.info("===> - 外部调用：userService.createUserWithException() → 代理对象调用 → 事务生效");
        logger.info("===> - 内部调用：this.createUserWithException() → 目标对象调用 → 事务失效");
        
        logger.info("===> 对比代理对象调用和目标对象调用结束: {}", username);
    }
    
    @Override
    public void verifyTargetObjectStorage(String username) {
        logger.info("===> 验证代理对象内部的目标对象存储开始: {}", username);
        
        // 1. 检查当前对象的类型
        logger.info("===> 当前对象类型: {}", this.getClass().getName());
        logger.info("===> 当前对象是否为代理: {}", this.getClass().getName().contains("$"));
        
        // 2. 尝试通过反射获取目标对象
        try {
            // 对于CGLIB代理，目标对象通常存储在target字段中
            java.lang.reflect.Field targetField = this.getClass().getDeclaredField("target");
            targetField.setAccessible(true);
            Object target = targetField.get(this);
            logger.info("===> 通过反射获取的目标对象: {}", target);
            logger.info("===> 目标对象类型: {}", target.getClass().getName());
            logger.info("===> 目标对象是否为代理: {}", target.getClass().getName().contains("$"));
        } catch (NoSuchFieldException e) {
            logger.info("===> 未找到target字段，可能不是CGLIB代理");
        } catch (Exception e) {
            logger.info("===> 获取目标对象失败: {}", e.getMessage());
        }
        
        // 3. 检查所有字段
        logger.info("===> 当前对象的所有字段:");
        java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();
        for (java.lang.reflect.Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                logger.info("===> 字段: {} = {}", field.getName(), value);
            } catch (Exception e) {
                logger.info("===> 字段: {} (无法获取值)", field.getName());
            }
        }
        
        // 4. 检查父类字段（CGLIB代理会继承目标类）
        Class<?> superClass = this.getClass().getSuperclass();
        if (superClass != null) {
            logger.info("===> 父类: {}", superClass.getName());
            logger.info("===> 父类字段:");
            java.lang.reflect.Field[] superFields = superClass.getDeclaredFields();
            for (java.lang.reflect.Field field : superFields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    logger.info("===> 父类字段: {} = {}", field.getName(), value);
                } catch (Exception e) {
                    logger.info("===> 父类字段: {} (无法获取值)", field.getName());
                }
            }
        }
        
        logger.info("===> 验证代理对象内部的目标对象存储结束: {}", username);
    }
    
    @Override
    public void demonstrateTargetObjectRetrieval(String username) {
        logger.info("===> 演示如何从代理对象获取目标对象开始: {}", username);
        
        // 1. 获取当前对象（代理对象）
        Object proxy = this;
        logger.info("===> 当前对象（代理对象）: {}", proxy);
        logger.info("===> 代理对象类型: {}", proxy.getClass().getName());
        
        // 2. 尝试获取目标对象
        Object target = getTargetObject(proxy);
        logger.info("===> 获取到的目标对象: {}", target);
        logger.info("===> 目标对象类型: {}", target.getClass().getName());
        
        // 3. 验证目标对象和代理对象的关系
        logger.info("===> 目标对象是否为代理: {}", target.getClass().getName().contains("$"));
        logger.info("===> 目标对象和代理对象是否为同一个: {}", target == proxy);
        
        // 4. 通过目标对象调用方法（事务失效）
        if (target instanceof UserService) {
            UserService targetService = (UserService) target;
            try {
                targetService.createUserWithException(username + "_target_direct");
            } catch (RuntimeException e) {
                logger.info("===> 目标对象直接调用异常: {}", e.getMessage());
            }
        }
        
        logger.info("===> 演示如何从代理对象获取目标对象结束: {}", username);
    }
    
    /**
     * 从代理对象中获取目标对象
     */
    private Object getTargetObject(Object proxy) {
		return null;
//        try {
//            // 对于CGLIB代理
//            Field targetField = proxy.getClass().getDeclaredField("target");
//            targetField.setAccessible(true);
//            return targetField.get(proxy);
//        } catch (NoSuchFieldException e) {
//            try {
//                // 对于JDK动态代理
//                Field hField = proxy.getClass().getSuperclass().getDeclaredField("h");
//                hField.setAccessible(true);
//                Object handler = hField.get(proxy);
//
//                Field targetField = handler.getClass().getDeclaredField("target");
//                targetField.setAccessible(true);
//                return targetField.get(handler);
//            } catch (Exception ex) {
//                logger.info("===> 无法获取目标对象: {}", ex.getMessage());
//                return null;
//            }
//        } catch (Exception e) {
//            logger.info("===> 获取目标对象失败: {}", e.getMessage());
//            return null;
//        }
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
    
//    @Override
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
