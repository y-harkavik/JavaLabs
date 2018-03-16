import org.junit.Assert;
import org.junit.Test;

public class TestForMathFunction {
    @Test
    public void functionTest() {
        long x = 1, y = 1;
        MathFunction mathFunction = new MathFunction();
        double expected = 1.65119;
        double actual = mathFunction.start(x,y);

        Assert.assertEquals("Тест не прошел, т.к. не равны значения.",expected, actual,0.00001);
    }
}
