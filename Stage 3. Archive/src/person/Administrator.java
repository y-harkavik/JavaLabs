package person;

import Law.Laws;

import java.util.Arrays;

public class Administrator extends Account {
    public Administrator(String password, String accountLogin, byte[] salt) {
        super(password, accountLogin, salt, Arrays.asList(Laws.ADMINISTRATOR));
    }
}
