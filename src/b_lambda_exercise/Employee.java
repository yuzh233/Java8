package b_lambda_exercise;

/**
 * @Author: z.yu
 * @Date: 2018/05/28 20:17
 * @Description:
 */
public class Employee {
    private String name;
    private double salary;
    private int age;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }

    public Employee(int id, String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.age = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String show() {
        return "test...";
    }
}
