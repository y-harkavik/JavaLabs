package Communicate.Message.Request.ClientRequest;

import Communicate.Message.Request.Request;


public class SetParserRequest extends Request {
    private int parserID;

    public SetParserRequest(int parserID) {
        this.parserID = parserID;
    }

    public int getParserID() {
        return parserID;
    }
}
