package mine.projects.log_print_demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 激活spring framework日志模块。
 * 测试框架日志打印是否正常。
 */
public class LogTest {
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);
    
    public static void main(String[] args) {
        logger.info("Logback 成功运行在 Java {}", System.getProperty("java.version"));
    }
}