package mine.archive.log_print_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerConflictTest {
    private static final Logger logger = LoggerFactory.getLogger(LoggerConflictTest.class);
    
    public static void main(String[] args) {
        logger.info("测试日志绑定冲突解决方案");
        logger.debug("调试信息 - 应只在DEBUG级别显示");
        
        // 检查绑定情况
        System.out.println("实际使用的日志绑定: " + 
            LoggerFactory.getILoggerFactory().getClass().getName());
    }
}