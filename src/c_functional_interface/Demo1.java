package c_functional_interface;

import b_lambda_exercise.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @Author: z.yu
 * @Date: 2018/05/29 22:36
 * <p>
 * Java8 内置的四大核心函数式接口
 * <p>
 * Consumer<T> : 消费型接口
 * void accept(T t);
 * <p>
 * Supplier<T> : 供给型接口
 * T get();
 * <p>
 * Function<T, R> : 函数型接口
 * R apply(T t);
 * <p>
 * Predicate<T> : 断言型接口
 * boolean test(T t);
 */
public class Demo1 {

    /**
     * Consumer<T> : 消费型接口
     * void accept(T t);
     */
    @Test
    public void test1() {
        //调用consumer方法，参数一：字符串；参数二：Lambda表达式
        consumer("测试消费型接口", (str) -> System.out.println(str));
    }

    public void consumer(String string, Consumer<String> consumer) {
        consumer.accept(string);
    }

    /**
     * Supplier<T> : 供给型接口
     * T get();
     */
    @Test
    public void test2() {
        //箭头右边是 Supplier 接口的具体方法实现
        List<Integer> numList = getNumList(50, () -> new Random().nextInt(100));
        for (Integer integer : numList) {
            System.out.println(integer);
        }
    }

    /**
     * 需求：产生指定个数的整数，并放入集合中
     */
    public List<Integer> getNumList(int count, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        //添加指定次数的数字到集合
        for (int i = 0; i < count; i++) {
            //从这个供给型接口的方法中获取值。get()这个方法在调用的时候通过 Lambda 指定。
            Integer integer = supplier.get();
            list.add(integer);
        }
        return list;
    }


    /**
     * Function<T, R> : 函数型接口
     * R apply(T t);
     */
    @Test
    public void test3() {
        Function<Employee, String> fun = (employee) -> employee.getName();
        String name = fun.apply(new Employee("lisa"));
        System.out.println(name);
    }

    /**
     * Predicate<T> : 断言型接口
     * boolean test(T t);
     */
    @Test
    public void test4() {
        List<String> list = Arrays.asList("NiHao", "Hello,World", "lisa", "ojbk");
        //长度大于4的才返回
        List<String> list1 = filterStr(list, (str) -> str.length() >= 5);
        for (String s : list1) {
            System.out.println(s);
        }
    }

    /**
     * 需求：将满足条件的字符串，放入集合中
     */
    public List<String> filterStr(List<String> stringList, Predicate<String> predicate) {
        List<String> list = new ArrayList<>();
        for (String s : stringList) {
            //符合断言型接口的方法才会放入集合，具体逻辑调用时通过 Lambda 决定。
            if (predicate.test(s)) {
                list.add(s);
            }
        }
        return list;
    }


}
