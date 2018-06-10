package e_streamAPI_exercise;

import d_streamAPI.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author: z.yu
 * @Date: 2018/06/10 13:01
 */
public class Demo1Test {

    /**
     * 1.	给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
     * 给定【1，2，3，4，5】， 应该返回【1，4，9，16，25】。
     */
    @Test
    public void test1() {
        int[] arr = {1, 2, 3, 4, 5};
        List list = new ArrayList();
        Arrays.stream(arr).map((x) -> x * x).forEach(list::add);
        list.forEach(System.out::println);
    }


    List list = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(101, "张三", 18, 9999.99),
            10, true, "test"
    );

    /**
     * 怎样用 map 和 reduce 方法数一数流中有多少个Employee呢？
     */
    @Test
    public void test2() {
        Optional reduce = list.stream().filter((x) -> x instanceof Employee).map((e) -> 1).reduce((a, b) -> Integer.sum((int) a, (int) b));
        System.out.println(reduce.get());
    }
}
