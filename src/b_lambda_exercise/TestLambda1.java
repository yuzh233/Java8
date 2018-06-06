package b_lambda_exercise;

/**
 * @Author: z.yu
 * @Date: 2018/05/28 20:31
 * @Description:
 */
public class TestLambda1 {

    public static String toUpper(String str,MyFunction1 fun){
        //传参数给接口方法
        return fun.getValue(str);
    }

    public static String subStr(String str,MyFunction1 fun){
        return fun.getValue(str);
    }

}
