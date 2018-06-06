package b_lambda_exercise;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: z.yu
 * @Date: 2018/05/28 20:15
 * @Description:
 */
public class Demo1 {

    Employee[] employees = {
            new Employee(20, "lisa", 8888.95),
            new Employee(22, "rose", 5600.50),
            new Employee(25, "jack", 4000.95),
            new Employee(40, "john", 10000),
            new Employee(10, "tom", 10),
    };
    List<Employee> employeeList = Arrays.asList(employees);

    /**
     * 调用 Collections.sort() 方法，通过定制排序比较两个 Employee（先按年龄比，年龄相同按姓名比），使用 Lambda 作为参数传递。
     */
    @Test
    public void exercise1() {
        //int compare(T o1, T o2);
        Collections.sort(employeeList, (e1, e2) -> {
            if (e1.getAge() != e2.getAge()) {
                return Integer.compare(e1.getAge(), e2.getAge());
            } else {
                return e1.getName().compareTo(e2.getName());
            }
        });

        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }

    @Test
    public void exercise2() {
        //测试字符串转大写
        String toUpper = TestLambda1.toUpper("hello,world!", (str) -> str.toUpperCase());
        System.out.println(toUpper);

        //测试字符串截取
        String subStr = TestLambda1.subStr("hello,world", (str) -> str.substring(4));
        System.out.println(subStr);
    }

    @Test
    public void exercise3() {
        // 计算两个long型参数的和
        Long sum = TestLambda2.sum(100L, 23L, (a, b) -> a + b);
        System.out.println(sum);

        // 计算两个long型参数的乘积
        Long area = TestLambda2.area((long) 5.5, 5L, (a, b) -> a * b);
        System.out.println(area);
    }
}
