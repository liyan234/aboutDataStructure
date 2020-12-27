package frank;

public class Duck2 {
    private String name;
    private Integer age;


    public Duck2(String n, Integer a) {
        this.name = n;
        this.age = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "duck{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
