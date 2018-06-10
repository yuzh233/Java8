package e_streamAPI_exercise;

import d_streamAPI.Demo3Test;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author: z.yu
 * @Date: 2018/06/10 13:28
 */
public class Demo2Test {
    List<Transaction> transactions = null;

    @Before
    public void before() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    /**
     * 1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
     */
    @Test
    public void test1() {
        transactions.stream()
                .filter((y) -> y.getYear() == 2011)
                .sorted((x, y) -> Integer.compare(x.getValue(), y.getValue()))
                .forEach(System.out::println);
    }

    /**
     * 2. 交易员都在哪些不同的城市工作过？
     */
    @Test
    public void test2() {
        transactions.stream()
                .map((t) -> t.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 3. 查找所有来自剑桥的交易员，并按姓名排序
     */
    @Test
    public void test3() {
        //打印的是交易对象
        transactions.stream()
                .filter((t) -> t.getTrader().getCity().equals("Cambridge"))
                .sorted((x, y) -> x.getTrader().getName().compareTo(y.getTrader().getName()))
                .distinct()
                .forEach(System.out::println);

        //打印的是交易员姓名
        transactions.stream()
                .filter((t) -> t.getTrader().getCity().equals("Cambridge"))
                .map((t) -> t.getTrader().getName())
                .sorted(String::compareTo)
                .forEach(System.out::println);
    }

    /**
     * 5. 有没有交易员是在米兰工作的？
     */
    @Test
    public void test5() {
        boolean b = transactions.stream()
                .anyMatch((t) -> t.getTrader().getCity().equals("Milan"));

        System.out.println(b);
    }

    /**
     * 6. 打印生活在剑桥的交易员的所有交易额
     */
    @Test
    public void test6() {
        Integer sum = transactions.stream()
                .filter((t) -> t.getTrader().getCity().equals("Cambridge"))
                .map((t) -> t.getValue())
                .reduce(0, Integer::sum);

        System.out.println(sum);
    }

    /**
     * 7. 所有交易中，最高的交易额是多少
     */
    @Test
    public void test7() {
        Optional<Integer> max = transactions.stream()
                .map((t) -> t.getValue())
                .max(Integer::compareTo);

        System.out.println(max.get());
    }

    /**
     * 8. 找到交易额最小的交易
     */
    @Test
    public void test8() {
        Optional<Transaction> first = transactions.stream()
                .sorted((x, y) -> Integer.compare(x.getValue(), y.getValue()))
                .findFirst();

        System.out.println(first.get());

        //老师答案 ----------------------------------
        Optional<Transaction> op = transactions.stream()
                .min((t1, t2) -> Integer.compare(t1.getValue(), t2.getValue()));

        System.out.println(op.get());
    }

    /**
     * 4. 返回所有交易员的姓名字符串，按字母顺序排序。
     */
    @Test
    public void test4() {
        //获取到所有交易员姓名并拼接
        String str = transactions.stream()
                .map((t) -> t.getTrader().getName())
                .sorted()
                .distinct()
                .reduce("", String::concat);

        /**
         * 1.根据交易员姓名映射到一个流
         * 2.流中的每一个元素调用 filterCharacter ，每个元素调用之后返回一个集合，集合里面放的原流中元素拆分后的单个字符。
         * 3.把每个元素拆分后的字符集合拼接起来组成成一个新的流
         * 4.对整个新流排序
         * 5.内部迭代
         */
        transactions.stream()
                .map((t) -> t.getTrader().getName())
                .flatMap(Demo2Test::filterCharacter)
                .sorted((s1, s2) -> s1.compareToIgnoreCase(s2))
                .forEach(System.out::print);

    }

    public static Stream<String> filterCharacter(String str) {
        List<String> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch.toString());
        }

        return list.stream();
    }
}
