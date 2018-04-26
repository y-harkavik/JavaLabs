package person;

import law.Laws;

import java.util.List;

public class User extends Person {
    protected List<Laws> userLaws;

    public User(String password, String accountLogin, byte[] salt, List<Laws> userLaws) {
        super(password, accountLogin, salt);
        this.userLaws = userLaws;
    }

    public List<Laws> getUserLaws() {
        return userLaws;
    }
}
