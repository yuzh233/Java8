package c_functional_interface;

import b_lambda_exercise.Employee;
import org.junit.Test;

import java.io.PrintStream;
import java.util.function.*;

/**
 * @Author: z.yu
 * @Date: 2018/05/29 23:16
 * <p>
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * <p>
 * 1. 对象的引用 :: 实例方法名
 * <p>
 * 2. 类名 :: 静态方法名
 * <p>
 * 3. 类名 :: 实例方法名
 * <p>
 * 注意：
 * ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * <p>
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * <p>
 * 1. 类名 :: new
 * <p>
 * 三、数组引用
 * <p>
 * 类型[] :: new;
 */
public class Demo2 {

    /**
     * 【引用特定对象的实例方法】
     * 对象的引用 :: 实例方法名
     */
    @Test
    public void test1() {
        /*没用方法引用的做法*/
        Consumer<String> consumer = (str) -> System.out.println(str);
        consumer.accept("test");

        /*使用对象的方法引用*/
        PrintStream ps = System.out;
        /*
            ps是System中的对象，println是其方法。注意：被调用的方法的参数和返回值要和函数式接口的方法的参数和返回值保持一致才能使用方法引用。
            被调用的方法：void println(String x)
            函数式接口的方法：void accept(T t);
         */
        Consumer<String> consumer1 = ps::println; //注意这里没有带参数
        consumer.accept("test1");
    }

    @Test
    public void test2() {
        /*
            T get();
         */
        //没使用方法引用
        Employee employee = new Employee("lisa");

        Supplier<String> supplier = () -> employee.getName(); //这里能使用employee对象实际上是匿名函数调用外部变量。
       /* Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return employee.getName();
            }
        };*/

        System.out.println(supplier.get());

        //使用方法引用
        Supplier<String> supplier1 = employee::getName;
        System.out.println(supplier1.get());
    }


    /**
     * 【引用静态方法】
     * 类名 :: 静态方法名
     */
    @Test
    public void test3() {
        //BiFunction是Function子类.
        //函数式方法：R apply(T t, U u);
        //被调用方法：int max(int a, int b)
        BiFunction<Integer, Integer, Integer> fun = Math::max;
        Integer max = fun.apply(100, 50);
        System.out.println(max);
    }

    /**
     * 【引用特定类型任意对象的实例方法】
     * 若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
     * 类名 :: 实例方法名
     */
    @Test
    public void test4() {
        //R apply(T t);
        //String show()

        //不使用方法调用，为什么t可以调用Employee的方法，因为通过类型推断已经确定t就是Employee对象
        Function<Employee, String> fun = (t) -> t.show();
        //使用方法调用
        fun = Employee::show;

        String apply = fun.apply(new Employee());
        System.out.println(apply);

        //boolean test(T t, U u);
        //boolean equals(Object anObject)
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        //函数式方法的第一个参数必须是被调用方法的调用者，第二个参数必须是被调用方法的参数
        biPredicate = String::equals;
        System.out.println(biPredicate.test("123", "123"));


        compare((x, y) -> x.compareTo(y), 1, 2);
        compare(Integer::compareTo, 1, 2);
    }

    public int compare(BiFunction<Integer, Integer, Integer> biFunction, int a, int b) {
        return biFunction.apply(a, b);
    }

    /**
     * 构造器引用
     * 1. 类名 :: new
     */
    @Test
    public void test5() {
        //通过get方法获取一个对象，T get() <==>  Employee() 参数返回值一致
        Supplier<Employee> supplier = Employee::new;
        Employee employee = supplier.get();
        System.out.println(employee);

        //通过apply获取一个对象，传入一个参数。R apply(T t) <==> Employee(String name) 参数返回值一致
        Function<String, Employee> fun = Employee::new;
        Employee rose = fun.apply("rose");
        System.out.println(rose);
    }

    /**
     * 数组引用
     */
    @Test
    public void test6() {
        //传入一个数组长度，返回一个String数组
//        Function<Integer,String[]> fun = (len) -> new String[len];

        //调用的是String的public String(String original)构造器
        Function<Integer, String[]> fun = String[]::new;

        String[] apply = fun.apply(10);
        System.out.println(apply.length);
    }

}
