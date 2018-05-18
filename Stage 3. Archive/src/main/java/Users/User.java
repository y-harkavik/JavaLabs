package Users;

import Law.Laws;

import java.util.List;

public class User extends Account {
    protected List<Laws> userLaws;

    public User(String password, String accountLogin, byte[] salt, List<Laws> userLaws) {
        super(password, accountLogin, salt, userLaws);
        this.userLaws = userLaws;
    }

    public List<Laws> getUserLaws() {
        return userLaws;
    }

    public void setUserLaws(List<Laws> userLaws) {
        this.userLaws = userLaws;
    }
}
