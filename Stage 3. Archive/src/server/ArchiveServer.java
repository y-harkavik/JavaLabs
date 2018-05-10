package server;

import client.Client;
import Communicate.Message.Request.ClientRequest.AuthenticationRequest;
import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.*;
import Law.Laws;
import password.UsersBase;
import person.Account;
import person.PersonnelFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static parser.Parsers.*;

public class ArchiveServer {
    private ServerSocket serverSocket;
    private UsersBase usersBase;
    private int currentParser;
    public static final int SERVER_PORT = 5000;
    public static final String SERVER_IP = "127.0.0.1";

    public ArchiveServer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            usersBase = new UsersBase();
            usersBase.getBaseFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket);
                Thread t = new Thread(new ClientHandler(client));
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ClientHandler implements Runnable {
        Client client;

        public ClientHandler(Client client) {
            this.client = client;
        }

        @Override
        public void run() {
            Request clientRequest;
            try {
                while ((clientRequest = (Request) client.getInputStream().readObject()) != null) {
                    if (clientRequest instanceof AuthenticationRequest) {
                        Account currentAccount = userAuthentication((AuthenticationRequest) clientRequest);
                        checkDatabaseResponseAndSendClientRequest(currentAccount);
                        continue;
                    }
                    handleClientRequest(clientRequest);

                }
            } catch (InterruptedException e) {
                closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void checkDatabaseResponseAndSendClientRequest(Account currentAccount) {
            if (currentAccount != null) {
                sendMessage(client, new AuthenticationResponse(ResponseType.GOOD,
                        null,
                        getListOfPersonnelFiles(),
                        null,
                        currentAccount.getLawsList()));
            } else {
                sendMessage(client, new AuthenticationResponse(ResponseType.ERROR,
                        TypeOfError.AUTHENTICATION_ERROR,
                        null,
                        null,
                        null));
            }
        }

        void closeConnection() {
            try {
                client.getOutputStream().close();
                client.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Integer> getListOfPersonnelFiles() {
        return null;
    }

    public PersonnelFile getPersonnelFileOfSpecificMen(String passportID) {
        PersonnelFile personnelFile = new PersonnelFile();
        switch (currentParser) {
            case DOM:
                break;
            case SAX:
                break;
            case StAX:
                break;
        }
        return null;
    }

    void handleClientRequest(Request clientRequest) throws InterruptedException {
        switch (clientRequest.getRequestType()) {
            case GET_PERSONNEL_FILE:
                break;
            case ADD:
                break;
            case READ:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case CHANGE_LAWS:
                break;
            case DISCONNECT:
                throw new InterruptedException();
        }

    }

    public Account userAuthentication(AuthenticationRequest authenticationRequest) {
        String login = authenticationRequest.getLogin();
        String password = authenticationRequest.getPassword();
        return usersBase.checkAccount(login, password);
    }

    void sendResponseForAdministrator(Client client, ResponseType responseType, String message, String passportID) {
        sendMessage(client, new ResponseForAdministrator(responseType,
                message,
                getListOfPersonnelFiles(),
                getPersonnelFileOfSpecificMen(passportID),
                null,
                usersBase.getListOfAccounts()));
    }

    void sendResponseForUser(Client client, ResponseType responseType, String message, String passportID, List<Laws> userLaws) {
        sendMessage(client, new ResponseForUser(responseType,
                message,
                getListOfPersonnelFiles(),
                getPersonnelFileOfSpecificMen(passportID),
                userLaws));
    }

    void sendMessage(Client client, Response serverResponse) {
        try {
            client.getOutputStream().writeObject(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
