package mine.projects.objenesis.configuration_full_and_lite;

public class ServiceA {
    private final ServiceB serviceB;
    public ServiceA(ServiceB serviceB) {
        this.serviceB = serviceB;
    }
    public ServiceB getServiceB() {
        return serviceB;
    }
}