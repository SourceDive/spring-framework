package mine.archive.annotation_bean_method_creatation.config;

import mine.archive.annotation_bean_method_creatation.domain.A;
import mine.archive.annotation_bean_method_creatation.domain.B;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean(name = "aaa")
    public A a() {
        return new A(b());  // 此处调用b()
    }
    
    @Bean(name = "bbb")
    public B b() {
        return new B();
    }
}