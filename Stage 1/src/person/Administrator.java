package person;

import Authentication.AuthModel;

public class Administrator extends Person{

    Administrator(String password, byte[] salt) {
        super(password, salt);
    }

    @Override
    public String toString() {
        return ADMIN;
    }
}
