package a_lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @Author: z.yu
 * @Date: 2018/05/28 17:56
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * 箭头操作符将 Lambda 表达式拆分成两部分：
 * <p>
 * 左侧：Lambda 表达式的参数列表
 * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
 * <p>
 * 语法格式一：无参数，无返回值
 * () -> System.out.println("Hello Lambda!");
 * <p>
 * 语法格式二：有一个参数，并且无返回值
 * (x) -> System.out.println(x)
 * <p>
 * 语法格式三：若只有一个参数，小括号可以省略不写
 * x -> System.out.println(x)
 * <p>
 * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
 * Comparator<Integer> com = (x, y) -> {
 * System.out.println("函数式接口");
 * return Integer.compare(x, y);
 * };
 * <p>
 * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
 * Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 * <p>
 * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 * (Integer x, Integer y) -> Integer.compare(x, y);
 * <p>
 * 上联：左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 * <p>
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 * 可以检查是否是函数式接口
 */
public class Demo1 {

    /**
     * 语法格式一：无参数，无返回值
     */
    @Test
    public void test1() {
        // 以Runnable接口为例，该接口是一个函数式接口
        //public abstract void run(); 实现的是这个唯一的抽象方法
        Runnable run = () -> System.out.println("hello lambda...");
        run.run();
    }

    /**
     * 语法格式二：有一个参数，并且无返回值
     */
    @Test
    public void test2() {
        // void accept(T t) 实现的是这个方法
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("parameter");
    }

    /**
     * 语法格式三：若只有一个参数，小括号可以省略不写
     */
    @Test
    public void test3() {
        Consumer<Integer> con = x -> System.out.println(x);
        con.accept(123);
    }

    /**
     * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
     */
    @Test
    public void test4() {
        //int compare(T o1, T o2);
        Comparator<Integer> comparator = (a, b) -> {
            return Integer.compare(a, b);
        };

        //大于返回1，小于返回-1，相等返回0
        System.out.println(comparator.compare(3, 2));
    }

    /**
     * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
     */
    @Test
    public void test5() {
        Comparator<Integer> comparator = (a, b) -> Integer.compare(a, b);
        System.out.println(comparator.compare(12, 12));
    }

    /**
     * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
     */
    @Test
    public void test6() {
        Comparator<Integer> comparator = (Integer a, Integer b) -> Integer.compare(a, b);
        // lambda 的参数可以不指定类型
        Comparator<Integer> comparator1 = (a, b) -> Integer.compare(a, b);
        System.out.println(comparator.compare(12, 12));
    }

    /**
     * 需求：对一个数进行运算
     */
    @Test
    public void testOperation() {
        //不使用lambda的方法
        System.out.println(operation(100, new MyFun() {
            //具体的运算逻辑
            @Override
            public Integer getResult(Integer num) {
                return num * num;
            }
        }));

        //使用Lambda。(x) -> x*x 就相当于上面实现接口的方法。
        System.out.println(operation(100, (x) -> x * x));

        //加法操作
        System.out.println(operation(100, (x) -> x + 10));

        // 做减法运算，可以不通过 operation() 间接的传参数给接口方法，直接通过接口对象调用即可！
        MyFun fun = (x) -> x - 100;
        System.out.println(fun.getResult(50));
    }

    /**
     * 这个方法主要是给执行运算的接口带入参数
     *
     * @param num 带入到运算方法的参数
     * @param fun 运算方法接口类型
     * @return 结果
     */
    public Integer operation(Integer num, MyFun fun) {
        //具体的运算逻辑（+，-，*，/ ....）丢给getResult方法，MyFun接口获得了这个参数，执行不同的运算逻辑，运算逻辑由Lambda表达式决定。
        return fun.getResult(num);
    }
}
