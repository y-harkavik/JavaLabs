package Server;

import Communicate.Message.Request.ClientRequest.AdministratorRequest;
import Parser.*;

import Users.Administrator;
import client.Client;
import Communicate.Message.Request.ClientRequest.AuthenticationRequest;
import Communicate.Message.Request.Request;
import Communicate.Message.Response.Response;
import Communicate.Message.Response.ServerResponse.*;
import Database.UserBase;
import Users.Account;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ArchiveServer {
    private ServerSocket serverSocket;
    private UserBase userBase;
    public static final int SERVER_PORT = 5000;
    public static final String SERVER_IP = "127.0.0.1";
    private static Parser currentParser;

    public ArchiveServer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            userBase = new UserBase();
            userBase.getBaseFromFile();
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
        Account currentAccount;

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
            } catch (SocketException | EOFException e) {
                closeConnection();
            } catch (InterruptedException e) {
                closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                userBase.saveBase();
            }
        }

        void checkDatabaseResponseAndSendClientRequest(Account currentAccount) {
            if (currentAccount != null) {
                this.currentAccount = currentAccount;
                sendMessage(client, new AuthenticationResponse(ResponseType.GOOD,
                        null,
                        currentParser.getMapOfLastNameAndID(),
                        currentAccount.getLawsList(),
                        getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
            } else {
                sendMessage(client, new AuthenticationResponse(ResponseType.ERROR,
                        TypeOfError.AUTHENTICATION_ERROR,
                        null,
                        null,
                        null));
            }
        }

        void handleClientRequest(Request clientRequest) throws InterruptedException {
            String report;
            switch (clientRequest.getRequestType()) {
                case GET_PERSONNEL_FILE:
                    sendMessage(client, new ResponseForAdministrator(
                            ResponseType.GOOD,
                            null,
                            currentParser.getMapOfLastNameAndID(),
                            currentParser.getPersonnelFile(clientRequest.getPreviousPassportID()),
                            getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
                    break;
                case ADD:
                    report = currentParser.insertPersonnelFileInXML(
                            clientRequest.getHandlingPersonnelFile(),
                            clientRequest.getHandlingPersonnelFile().getBasicInformation().getPassport());
                    if (report == null) {
                        sendMessage(client, new ResponseForAdministrator(
                                ResponseType.GOOD,
                                null,
                                currentParser.getMapOfLastNameAndID(),
                                currentParser.getPersonnelFile(clientRequest.getHandlingPersonnelFile().getBasicInformation().getPassport()),
                                getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
                    } else {
                        sendMessage(client, new ResponseForAdministrator(
                                ResponseType.ERROR,
                                report,
                                currentParser.getMapOfLastNameAndID(),
                                null,
                                getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
                    }
                    break;
                case DELETE:
                    currentParser.removePersonnelFileFromXML(clientRequest.getPreviousPassportID());
                    sendMessage(client, new ResponseForAdministrator(
                            ResponseType.GOOD,
                            null,
                            currentParser.getMapOfLastNameAndID(),
                            null,
                            getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
                    break;
                case UPDATE:
                    report = currentParser.updatePersonInXML(
                            clientRequest.getHandlingPersonnelFile(),
                            clientRequest.getPreviousPassportID());
                    if (report == null) {
                        sendMessage(client, new ResponseForAdministrator(
                                ResponseType.GOOD,
                                null,
                                currentParser.getMapOfLastNameAndID(),
                                currentParser.getPersonnelFile(clientRequest.getHandlingPersonnelFile().getBasicInformation().getPassport()),
                                getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
                    } else {
                        sendMessage(client, new ResponseForAdministrator(
                                ResponseType.ERROR,
                                report,
                                currentParser.getMapOfLastNameAndID(),
                                currentParser.getPersonnelFile(clientRequest.getPreviousPassportID()),
                                getMapOfAccountsWithoutCurrentUserAndAdmins(currentAccount)));
                    }
                    break;
                case CHANGE_LAWS:
                    userBase.getMapOfAccounts().putAll(((AdministratorRequest) clientRequest).getChangingAccountMap());
                    break;
                case DISCONNECT:
                    throw new InterruptedException();
            }
            currentParser.saveInFile();
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

    public Map<String, Account> getMapOfAccountsWithoutCurrentUserAndAdmins(Account currentAccount) {
        Map<String, Account> accounts = new HashMap<>(userBase.getMapOfAccounts());
        for (Iterator<Account> iterator = accounts.values().iterator(); iterator.hasNext(); ) {
            Account account = iterator.next();
            if (account instanceof Administrator || currentAccount == account) {
                iterator.remove();
            }
        }
        return accounts;
    }

    public Account userAuthentication(AuthenticationRequest authenticationRequest) {
        String login = authenticationRequest.getLogin();
        String password = authenticationRequest.getPassword();
        return userBase.checkAccount(login, password);
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
