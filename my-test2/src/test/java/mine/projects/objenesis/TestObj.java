package mine.projects.objenesis;

public class TestObj {

    private final long userId;

    private String name;

    private int age;

    public TestObj(long userId){
        //todo check userId unique, or else throw Exception
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}