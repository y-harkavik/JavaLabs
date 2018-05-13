package Communicate.Message.Response.ServerResponse;

import java.io.Serializable;

public class TypeOfError implements Serializable {
    public static final String AUTHENTICATION_ERROR = "Login or Database entered incorrect";
    public static final String DATA_ERROR = "data error";

    private TypeOfError() {
    }
}
