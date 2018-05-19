package Users;

import Law.Laws;

import java.util.List;

public class User extends Account {

    public User(String password, String accountLogin, byte[] salt, List<Laws> userLaws) {
        super(password, accountLogin, salt, userLaws);
    }
}
