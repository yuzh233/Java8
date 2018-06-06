package d_streamAPI;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * StreamAPI
 *
 * @Author: z.yu
 * @Date: 2018/06/05 22:20
 * @Desciption: 流（Stream）：非IO流，这里的Stream更像是具有iterator的集合类，但是又和集合类不同。流是用来操作数据源（集合、数组等）的元素序列的。
 * 对数据源中的元素进行计算（比如筛选、排序、比较、归纳、遍历。。。。）
 * <p>
 * 注意：stream自己不会存储元素
 * stream不会改变源数据，它会返回一个持有结果之后的新的stream
 * stream的操作是延迟执行的，对其的所有操作都是在需要结果的时候才执行的
 * <p>
 * 使用Stream的三个步骤：
 * 一、创建流
 * 顾名思义，就是从一个数据源（如集合、数组）中，获取流。
 * 二、操作流
 * 对数据源生成的流进行操作（）
 * 三、终止流
 * 终止流，一般都是通过流产生一个集合或数组（对源数据进行操作之后的新数据），执行终止流，会执行流的中间操作，并产生结果。
 */
public class Demo1 {

    //---------- 创建流 --------------//

    /**
     * 一、从 Collection 结果中获取流
     * default Stream<E> stream() : 返回一个顺序流
     * default Stream<E> parallelStream() : 返回一个并行流
     * <p>
     * 先不管两个流有什么区别，首先发现这两个方法的修饰符是 default ？！ 没见过~~
     * ## default 修饰符是jdk1.8对接口新增的特性。 ##
     * 场景：当一个接口被多个类所实现时，如果对该接口新增一个抽象方法（接口中的方法默认是抽象的，复习一下），那么其每个实现类都要修改，造成很大的更改。
     * 而使用 default 修饰方法可以避免这个问题。
     * 一个问题：当一个类实现A、B两个接口，并且A、B中都有一个相同的方法名相同参数列表相同的方法，这时实现类就会编译报错，注意一下！
     * ## static java8 中对接口增加static修饰符，接口也可以有静态方法了。
     */
    @Test
    public void createStream1() {
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        //获取顺序流
        Stream<String> stream = list.stream();
        //获取并行流
        Stream<String> stringStream = list.parallelStream();
    }

    /**
     * 二、由数组创建流
     * Java8 中的 Arrays 的静态方法 stream() 可以获取数组流：
     * <p>
     * static <T> Stream<T> stream(T[] array): 返回一个流
     * <p>
     * 重载方法，处理对应基本类型的数组：
     * public static IntStream stream(int[] array)
     * public static LongStream stream(long[] array)
     * public static DoubleStream stream(double[] array)
     */
    @Test
    public void createStream2() {
        int[] numbers = {1, 2, 3, 4, 5, 6};
        //获得int类型的流
        IntStream stream = Arrays.stream(numbers);

        double[] price = {12.4, 12.5};
        //获取一个double类型的流
        DoubleStream doubleStream = Arrays.stream(price);
    }

    /**
     * * 三、由值创建流
     * Stream.of(), 通过显示值创建一个流。它可以接收任意数量的参数。
     * public static<T> Stream<T> of(T... values) : 返回一个流
     */
    @Test
    public void createStream3() {
        Stream<int[]> stream = Stream.of(new int[]{1, 2, 3, 4, 5, 6});
        Stream<String> stringStream = Stream.of(new String[]{"aa", "bb", "cc", "dd"});
        Stream<double[]> doubleStream = Stream.of(new double[]{12.4, 12.5});
    }

    /**
     * 四、由函数创建无限流
     * 静态方法 Stream.iterate() 和 Stream.generate(), 创建无限流。
     * 1.迭代
     * public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
     * 2.生成
     * public static<T> Stream<T> generate(Supplier<T> s) :
     */
    @Test
    public void createStream4() {
        Random random = new Random();
        //生成无限流
//        Stream.generate( () -> new Random().nextInt() ); //不使用方法引用

        /**
         * 使用方法引用 实例::方法名
         *  Stream.generate(random::nextInt)
         * 因为该方法中的参数类型就是一个供给型接口
         * 所以random::nextInt是该接口中方法的方法体
         */
        Stream<Integer> generate = Stream.generate(random::nextInt);

        //迭代无限流
        Stream<Integer> stream = Stream.iterate(100, (x) -> x + 1);
        stream.forEach(System.out::println);
    }


}
