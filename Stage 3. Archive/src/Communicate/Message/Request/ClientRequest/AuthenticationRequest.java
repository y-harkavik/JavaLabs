package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;

public class AuthenticationRequest extends Request {
    private String login;
    private String password;

    public AuthenticationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
