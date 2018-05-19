package Persons;

public class Administrator extends Person {

    public Administrator(String password, String login, byte[] salt) {
        super(password, login, salt);
    }

    @Override
    public String toString() {
        return ADMIN;
    }
}
