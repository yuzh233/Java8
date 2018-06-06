package d_streamAPI;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流的终止操作
 *
 * @Author: z.yu
 * @Date: 2018/06/06 11:15
 * @Desciption: 从流的流水线生成结果。其结果可以是任何不是流的值，例如： List、 Integer，甚至是 void
 */
public class Demo3Test {

    List<Employee> employees = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    /**
     * 查找与匹配
     * allMatch(Predicate p) 检查是否匹配所有元素
     * anyMatch(Predicate p) 检查是否至少匹配一个元素
     * noneMatch(Predicate p) 检查是否没有匹配所有元素
     * findFirst() 返回第一个元素
     * findAny() 返回当前流中的任意元素
     * count() 返回流中元素总数
     * max(Comparator c) 返回流中最大值
     * min(Comparator c) 返回流中最小值
     * forEach(Consumer c) 内部迭代(使用 Collection 接口需要用代，称为外部迭代。相反， Stream API迭代——它帮你把迭代做了)
     */
    @Test
    public void test1() {
        boolean b = employees.stream()
                .allMatch((e) -> e.getAge() != 0);
        System.out.println(b);

        System.out.println("-------------------------");
        b = employees.stream()
                .anyMatch((e) -> e.getAge() == 59);
        System.out.println(b);

        System.out.println("-------------------------");
        b = employees.stream()
                .noneMatch((e) -> e.getAge() == 100);
        System.out.println(b);

        System.out.println("-------------------------");
        //放入Optional容器不会抛出空指针异常
        Optional<Employee> first = employees.stream()
                .findFirst();
        System.out.println(first.get());

        System.out.println("-------------------------");
        Optional<Employee> any = employees.stream().filter((e) -> e.getAge() < 28)
                .findAny();
        System.out.println(any.get());

        System.out.println("-------------------------");
        long count = employees.stream()
                .count();
        System.out.println(count);

        System.out.println("-------------------------");
        //找年龄最大的员工
        Optional<Employee> maxEmployeeByAge = employees.stream()
                .max((x, y) -> Integer.compare(x.getAge(), y.getAge()));
        System.out.println(maxEmployeeByAge.get());
        //找年龄最大的是多少岁
        Optional<Integer> maxAge = employees.stream().map(Employee::getAge)
                .max(Integer::compareTo);
        System.out.println(maxAge.get());
    }

    /**
     * 归约
     * reduce(T identity, BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。 返回 T
     * 参数一：初始值
     * 参数二：二元操作
     * <p>
     * reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
     * <p>
     * 两者的区别是：1.是否可以设置初始值 2.是否有可能会抛出空指针异常。
     * 备注：map 和 reduce 的连接通常称为 map-reduce 模式，因 Google 用它来进行网络搜索而出名。
     */
    @Test
    public void test2() {
        //求和：
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer reduce = list.stream().reduce(100, (a, b) -> a + b);
        System.out.println(reduce);

        System.out.println("------------------");
        Optional<Integer> reduce1 = list.stream().reduce((a, b) -> a + b);
        System.out.println(reduce1.get());

        System.out.println("------------------");
        /**
         * 需求：搜索名字中 “六” 出现的次数
         */
        //方式一：对每一个Employee的name拆分出来再合并成一个流，再把这个流映射成元素为1或0的新流，对这个新流归约操作得出出现的次数
        Optional<Integer> num = employees.stream().flatMap(Demo3Test::flapMap)
                .map((ch) -> {
                    //比较char要用 单引号
                    if (ch.equals('六')) {
                        return 1;
                    } else {
                        return 0;
                    }
                }).reduce((a, b) -> a + b);
        System.out.println(num.get());
        //方式二，更简洁
        long count = employees.stream().flatMap(Demo3Test::flapMap).filter((ch) -> ch.equals('六')).count();
        System.out.println(count);
    }

    public static Stream<Character> flapMap(Employee employee) {
        List list = new ArrayList<Character>();
        for (int i = 0; i < employee.getName().length(); i++) {
            list.add(employee.getName().charAt(i));
        }
        return list.stream();
    }


    /**
     * 收集
     * collect(Collector c) 将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法。
     * <p>
     * toList List<T> 把流中元素收集到List
     * toSet Set<T> 把流中元素收集到Set
     * toCollection Collection<T> 把流中元素收集到创建的集合
     * toMap Map<K,V>
     * counting Long 计算流中元素的个数
     * summingInt Integer 对流中元素的整数属性求和
     * averagingInt Double 计算流中元素Integer属性的平均值
     * summarizingInt IntSummaryStatistics 收集流中Integer属性的统计值。如：平均值
     * joining String 连接流中每个字符串
     * maxBy Optional<T> 根据比较器选择最大值
     * minBy Optional<T> 根据比较器选择最小值
     * reducing 归约产生的类型 从一个作为累加器的初始值开始，利用BinaryOperator与流中元素逐个结合，从而归约成单个值
     * collectingAndThen 转换函数返回的类型 包裹另一个收集器，对其结果转换函数
     * groupingBy Map<K, List<T>> 根据某属性值对流中的每一个元素分组，属性为K，结果为V
     * partitioningBy Map<Boolean, List<T>> 根据true或false进行分区
     */
    @Test
    public void test3() {
        //toList List<T>
        List<Employee> employeeList = employees.stream().filter((e) -> e.getName().contains("六")).collect(Collectors.toList());
        employeeList.forEach(System.out::println);

        //toSet Set<T>
        System.out.println("-----------------------");
        Set<Employee> employeeSet = employees.stream().collect(Collectors.toSet());
        employeeSet.forEach(System.out::println);

        //toCollection Collection<T>
        System.out.println("-----------------------");
        HashSet<String> hashSet = employees.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);

        //toMap Map<K,V>
        System.out.println("-----------------------");
        Map<String, Double> map = employees.stream().distinct().collect(Collectors.toMap(Employee::getName, Employee::getSalary));
        map.forEach((key, value) -> System.out.println(key + " -> " + value));

        //counting Long
        System.out.println("-----------------------");
        Long count = employees.stream().collect(Collectors.counting());
        System.out.println(count);

        //summingInt Integer
        System.out.println("-----------------------");
        Integer totalAge = employees.stream().collect(Collectors.summingInt(Employee::getAge));
        System.out.println(totalAge);

        //averagingInt Double
        System.out.println("-----------------------");
        Double avgAge = employees.stream().collect(Collectors.averagingInt(Employee::getAge));
        System.out.println(avgAge);

        //summarizingInt IntSummaryStatistics
        System.out.println("-----------------------");
        IntSummaryStatistics su = employees.stream().collect(Collectors.summarizingInt(Employee::getAge));
        System.out.println(su);

        //joining String
        System.out.println("-----------------------");
        String names = employees.stream().map(Employee::getName).collect(Collectors.joining(",", "[", "]"));
        System.out.println(names);

        //maxBy Optional<T>
        System.out.println("-----------------------");
        //(a,b) -> a.compareTo(b)  <==> Integer::compare   当第一个参数是调用者第二个参数是被调用者时可以这样写
        Optional<Integer> max = employees.stream().map(Employee::getAge).collect(Collectors.maxBy(Integer::compare));
        System.out.println(max.get());

        //reducing 归约产生的类型 ？？
        System.out.println("-----------------------");
        Integer sum = employees.stream().collect(Collectors.reducing(0, Employee::getAge, Integer::sum));
//        Optional<Double> sum = employees.stream().map(Employee::getSalary).collect(Collectors.reducing(Double::sum));
        System.out.println(sum);

        //collectingAndThen（搜集然后。。） 转换函数返回的类型 ？或许是第二个参数可以对collectingAndThen()中的第一个参数加工吧
        System.out.println("-----------------------");
        //把流转换成集合，再对集合自己获取size
        Integer integer = employees.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
        System.out.println(integer);
        //先对流求平均值，然后对这个平均值+10
        Double temp = employees.stream().collect(Collectors.collectingAndThen(Collectors.averagingDouble(Employee::getSalary), (s) -> s + 10));
        System.out.println(temp);
        //先对流求平均值，然后把这个平均值转换成String类型
        String tempStr = employees.stream().collect(Collectors.collectingAndThen(Collectors.averagingDouble(Employee::getSalary), (s) -> String.valueOf(s)));
        System.out.println(tempStr+"this is String");

        //groupingBy Map<K, List<T>>
        //根据年龄对流中的每一个元素分组
        System.out.println("-----------------------");
        Map<Integer, List<Employee>> collect = employees.stream().collect(Collectors.groupingBy(Employee::getAge));
        collect.forEach((k, v) -> System.out.println(k + " -> " + v));

        //partitioningBy Map<Boolean, List<T>>
        System.out.println("-----------------------");
        Map<Boolean, List<Employee>> collect1 = employees.stream().collect(Collectors.partitioningBy((x) -> x.getAge() > 50));
        collect1.forEach((k, v) -> System.out.println(k + "->" + v));
    }
}
