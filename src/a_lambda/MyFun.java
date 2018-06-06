package a_lambda;

/**
 * @Author: z.yu
 * @Date: 2018/05/28 18:22
 * @Description:
 */
@FunctionalInterface
public interface MyFun {

    /**
     * 进行数值运算，由Lambada 表达式来实现具体的不同的运算。
     * @param num 对传入的值做什么操作由Lambda表达式决定
     * @return 运算结果
     */
    Integer getResult(Integer num);
}
