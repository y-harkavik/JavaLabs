package Authentication;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestForAuthModel {
    private static AuthModel authModel;

    @BeforeClass
    public static void initAuthModel() {
        authModel = new AuthModel();
        authModel.getBaseOfAccounts().getBaseFromFile();
    }

    @Test
    public void testInputIncorrectInfo() {
        String login = "admin";
        String password = "12345";

        int expected = -1;
        int actual = authModel.checkEnteredInformation(login, password);
        Assert.assertEquals("Тест не пройден, т.к. при вводе не существуюшего пользователя произошла авторизация.", expected, actual);
    }

    @Test
    public void testInputCorrectUser() {
        String login = "admin";
        String password = "admin";

        int expected = 0;
        int actual = authModel.checkEnteredInformation(login, password);
        Assert.assertEquals("Тест не пройден, т.к. существующий пользователь не найден.", expected, actual);
    }
}
