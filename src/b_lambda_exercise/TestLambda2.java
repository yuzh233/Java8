package b_lambda_exercise;

/**
 * @Author: z.yu
 * @Date: 2018/05/28 20:39
 * @Description:
 */
public class TestLambda2 {

    /**
     * 计算两个long型参数的和
     */
    public static Long sum(Long a, Long b, MyFunction2<Long, Long> fun) {
        return fun.operation(a, b);
    }

    /**
     * 计算两个long型参数的乘积
     */
    public static Long area(Long a, Long b, MyFunction2<Long, Long> fun) {
        return fun.operation(a, b);
    }

}
