package mine.archive.jdk_proxy_manual;

/**
 * 简单接口的实现类
 */
public class SimpleInterfaceImpl implements SimpleInterface {
    
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
    
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
