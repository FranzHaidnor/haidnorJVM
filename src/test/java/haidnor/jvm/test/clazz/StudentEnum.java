package haidnor.jvm.test.clazz;

public enum StudentEnum {

    ZHANG_SAN(1,"张三");

    private int age;
    private String name;

    StudentEnum(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}
