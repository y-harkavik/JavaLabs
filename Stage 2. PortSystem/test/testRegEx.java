import RegEx.RegEx;
import org.junit.Assert;
import org.junit.Test;

public class testRegEx {

    @Test
    public void testName() {
        Assert.assertEquals("Name wrong",RegEx.checkName("a"),true);
        Assert.assertEquals("Name wrong",RegEx.checkName("a 1"),true);
        Assert.assertEquals("Name wrong",RegEx.checkName(""),false);
    }

    @Test
    public void testNum() {
        Assert.assertEquals("Num wrong",RegEx.checkNum(""),false);
        Assert.assertEquals("Num wrong",RegEx.checkNum("a"),false);
        Assert.assertEquals("Num wrong",RegEx.checkNum("1000000"),false);
        Assert.assertEquals("Num wrong",RegEx.checkNum("1"),true);
        Assert.assertEquals("Num wrong",RegEx.checkNum("-1"),false);
    }
}
