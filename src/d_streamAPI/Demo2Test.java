package d_streamAPI;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 流的中间操作
 *
 * @Author: z.yu
 * @Date: 2018/06/06 9:57
 */
public class Demo2Test {

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
     * 筛选与切片
     * filter(Predicate p) 接收 Lambda ， 从流中排除某些元素。
     * distinct() 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
     * limit(long maxSize) 截断流，使其元素不超过给定数量。
     * skip(long n) 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
     */
    @Test
    public void test1() {
        //把集合转换成流
        employees.stream()
                //对流中的每一个元素执行过滤方法
                .filter((e) -> e.getAge() > 20 && e.getAge() < 50)
                //内部迭代过滤之后的流，ForEach中的参数是Consumer接口，会把Foreach中每一个元素传给accept(T t)
                .forEach(System.out::println);

        System.out.println("---------------------------------------");
        employees.stream().distinct().forEach(System.out::println);

        System.out.println("---------------------------------------");
        employees.stream().limit(3).forEach(System.out::println);

        System.out.println("---------------------------------------");
        employees.stream().skip(5).forEach(System.out::println);
    }

    /**
     * 映射
     * map(Function f)
     * 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
     * mapToDouble(ToDoubleFunction f)
     * 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
     * mapToInt(ToIntFunction f)、mapToLong(ToLongFunction f)
     * <p>
     * flatMap(Function f)
     * 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test2() {
        //把集合转换成流，流的元素类型是Employee，对流进行映射操作：获取每一个Employee的age映射到一个Map中（map中传入一个Function接口（传入一个T返回一个R）），
        employees.stream().map((e) -> e.getAge() + 100).forEach(System.out::println);

        //把流中的每一个salary映射成map， R apply(T t); T 通过类型推断出是Employee，R推断出是double
        System.out.println("----------------------------");
        employees.stream().map(Employee::getSalary).forEach(System.out::println);

        //对流中每一个employee的salary设置成0之后返回这个employee
        System.out.println("----------------------------");
        employees.stream()
                .map((e) -> {
                    e.setSalary(0);
                    return e;
                })
                .forEach(System.out::println);


        System.out.println("----------------------------");
        employees.stream().mapToInt(Employee::getAge).forEach(System.out::println);

        //把流中的每一个Employee的name拆分放入list形成新的流，把每一个list的新流拼接成一个流返回迭代。
        System.out.println("----------------------------");
        employees.stream().flatMap(Demo2Test::flapMap).forEach(System.out::println);
    }

    public static Stream<Character> flapMap(Employee employee) {
        List list = new ArrayList<Character>();
        for (int i = 0; i < employee.getName().length(); i++) {
            list.add(employee.getName().charAt(i));
        }
        list.add(",");
        return list.stream();
    }

    /**
     * 排序
     * sorted() 产生一个新流，其中按自然顺序排序
     * sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序
     */
    @Test
    public void test3() {
        //自然排序
        employees.stream().map( Employee::getSalary ).sorted().forEach(System.out::println);

        //比较器排序  接口方法：int compare(T o1, T o2);
        System.out.println("----------------------------");
        employees.stream().sorted( (x,y) -> {
            if(x.getAge() == y.getAge()){
                return x.getName().compareTo(x.getName());
            }else {
                return Integer.compare(x.getAge(), y.getAge());
            }
        } ).forEach(System.out::println);
    }



}
