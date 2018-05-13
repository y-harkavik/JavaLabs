package Server;

import Communicate.Message.Request.ClientRequest.SetParserRequest;
import Parser.*;

import client.Client;
import Communicate.Message.Request.ClientRequest.AuthenticationRequest;
import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.*;
import Law.Laws;
import Database.UsersBase;
import Users.Account;
import Users.PersonnelFile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import static Parser.Parsers.*;

public class ArchiveServer {
    private ServerSocket serverSocket;
    private UsersBase usersBase;
    public static final int SERVER_PORT = 5000;
    public static final String SERVER_IP = "127.0.0.1";
    private static Parser currentParser;

    public ArchiveServer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            usersBase = new UsersBase();
            usersBase.getBaseFromFile();
            currentParser = DOMParser.getInstance();
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
                    if (clientRequest instanceof SetParserRequest) {
                        setCurrentParser(((SetParserRequest) clientRequest).getParserID());
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
                        currentParser.getMapOfLastNameAndID(),
                        currentAccount.getLawsList(),
                        getMapOfAccounts()));
            } else {
                sendMessage(client, new AuthenticationResponse(ResponseType.ERROR,
                        TypeOfError.AUTHENTICATION_ERROR,
                        null,
                        null,
                        null));
            }
        }

        void handleClientRequest(Request clientRequest) throws InterruptedException {
            switch (clientRequest.getRequestType()) {
                case GET_PERSONNEL_FILE:
                    sendMessage(client, new ResponseForAdministrator(
                            ResponseType.GOOD,
                            null,
                            currentParser.getMapOfLastNameAndID(),
                            currentParser.getPersonnelFile(clientRequest.getPreviousPassportID()),
                            null,
                            getMapOfAccounts()));
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

        void closeConnection() {
            try {
                client.getOutputStream().close();
                client.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void setCurrentParser(int id) {
        switch (id) {
            case DOM:
                currentParser = DOMParser.getInstance();
                break;
            case StAX:
                currentParser = StAXParser.getInstance();
                break;
            case SAX:
                currentParser = SAXParser.getInstance();
                break;
        }
    }

    public Map<String, Account> getMapOfAccounts() {
        return null;
    }

    public Account userAuthentication(AuthenticationRequest authenticationRequest) {
        String login = authenticationRequest.getLogin();
        String password = authenticationRequest.getPassword();
        return usersBase.checkAccount(login, password);
    }

    void sendMessage(Client client, Response serverResponse) {
        try {
            client.getOutputStream().writeObject(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> new ArchiveServer().startServer()).start();
    }
}
