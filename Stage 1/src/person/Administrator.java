package person;

import Authentication.AuthModel;

public class Administrator extends Person{

    Administrator(String password,String login, byte[] salt) {
        super(password,login, salt);
    }

    @Override
    public String toString() {
        return ADMIN;
    }
}
