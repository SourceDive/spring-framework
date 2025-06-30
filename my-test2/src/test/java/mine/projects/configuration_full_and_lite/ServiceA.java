package mine.projects.configuration_full_and_lite;

import org.springframework.stereotype.Component;

public class ServiceA {
    private final ServiceB serviceB;
    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
    }
    public ServiceB getServiceB() {
        return serviceB;
    }
}