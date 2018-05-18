package Communicate.Message.Request.ClientRequest;

import java.io.Serializable;

public enum RequestType implements Serializable {
    UPDATE, DELETE, ADD, DISCONNECT, CHANGE_LAWS, CREATE_USER, GET_PERSONNEL_FILE;
}
