import Client.Client;
import Communicate.Message.Request.ClientRequest.AdministratorRequest;
import Communicate.Message.Request.ClientRequest.AuthenticationRequest;
import Communicate.Message.Request.ClientRequest.RequestType;
import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.ResponseType;
import Server.ArchiveServer;
import Users.Job;
import Users.PersonnelFile;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class TestServer {
    static Client currentClient;

    @BeforeClass
    public static void connect() {
        try {
            currentClient = new Client(new Socket(ArchiveServer.SERVER_IP, ArchiveServer.SERVER_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAuthentication() {
        try {
            sendAuthMessage("admin", "admin");
            Response response = (Response) currentClient.getInputStream().readObject();
            Assert.assertEquals(ResponseType.GOOD, response.getResponseType());

            sendAuthMessage("ivan", "ivan");
            response = (Response) currentClient.getInputStream().readObject();
            Assert.assertEquals(ResponseType.ERROR, response.getResponseType());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRequest() {
        try {
            PersonnelFile personnelFile = new PersonnelFile();
            personnelFile.setJobs(new ArrayList<>());

            sendMessage(new AdministratorRequest(RequestType.ADD, personnelFile, "aasdsad", null));
            Response response = (Response) currentClient.getInputStream().readObject();
            Assert.assertEquals(ResponseType.ERROR, response.getResponseType());

            sendMessage(new AdministratorRequest(RequestType.UPDATE, personnelFile, "skfgsdps", null));
            response = (Response) currentClient.getInputStream().readObject();
            Assert.assertEquals(ResponseType.ERROR, response.getResponseType());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void sendAuthMessage(String login, String password) {
        try {
            currentClient.getOutputStream().writeObject(new AuthenticationRequest(login, password));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void closeConnection() {
        try {
            currentClient.getInputStream().close();
            currentClient.getOutputStream().close();
            currentClient.getClientSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(Request clientRequest) {
        try {
            currentClient.getOutputStream().writeObject(clientRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
