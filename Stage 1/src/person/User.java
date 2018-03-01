package person;

public class User extends Person{
    public static final long SIZE_OF_DATA_10MB = 10485760;

    User(String password, byte[] salt) {
        super(password, salt);
    }

    @Override
    public String toString() {
        return USER;
    }
}
