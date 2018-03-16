package person;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class testBase {
    private static Base base;

    @BeforeClass
    public static void initBase() {
        base = new Base();
        base.getBaseFromFile();
    }

    @Test
    public void testGetSaltedHash() {
        byte[]salt = base.getListOfAccounts().get(0).salt;
        String actualPassword = base.getListOfAccounts().get(0).passwordAndSaltHash;

        Assert.assertEquals("Не пройден тест, т.к. не совпадают хэши", base.getSaltedHash("admin",salt),actualPassword);
    }
}
