package communicate.message.Request.ClientRequest;

import communicate.message.Request.Request;

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
