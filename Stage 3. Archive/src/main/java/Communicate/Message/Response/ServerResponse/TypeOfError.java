package Communicate.Message.Response.ServerResponse;

import java.io.Serializable;

public class TypeOfError implements Serializable {
    public static final String AUTHENTICATION_ERROR = "Login or Database entered incorrect.";
    public static final String PASSPORT_ID_ERROR = "This PassportID is already taken.";

    private TypeOfError() {
    }
}
