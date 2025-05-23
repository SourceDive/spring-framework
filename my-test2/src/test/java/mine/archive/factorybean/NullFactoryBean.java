package mine.archive.factorybean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 定义一个返回null的FactoryBean
 */
public class NullFactoryBean implements FactoryBean<Object> {
    @Override
    public Object getObject() {
        return null; // 明确返回 null
    }

    @Override
    public Class<?> getObjectType() {
        return Object.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}