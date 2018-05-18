package Client;

import java.io.*;
import java.net.Socket;

public class Client {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Socket clientSocket;

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            this.outputStream = new ObjectOutputStream(outputStream);
            this.inputStream = new ObjectInputStream(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
