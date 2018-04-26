package person;

public class Admin extends Person {
    public Admin(String password, String accountLogin, byte[] salt) {
        super(password, accountLogin, salt);
    }
}
