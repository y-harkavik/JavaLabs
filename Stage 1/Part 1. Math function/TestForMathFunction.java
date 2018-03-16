import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestForMathFunction {
    private static MathFunction mathFunction;
    @BeforeClass
    public static void initMathFunc() {
        mathFunction = new MathFunction();
    }
    @Test
    public void unctionTest1() {
        long x = 1, y = 1;
        double expected = 1.65119;
        double actual = mathFunction.start(x,y);

        Assert.assertEquals("Тест не прошел, т.к. не равны значения.",expected, actual,0.00001);
    }

    @Test
    public void functionTest2() {
        long x = 2, y = 0;
        double expected = 2.32559;
        double actual = mathFunction.start(x,y);

        Assert.assertEquals("Тест не прошел, т.к. не равны значения.",expected, actual,0.00001);
    }
}
