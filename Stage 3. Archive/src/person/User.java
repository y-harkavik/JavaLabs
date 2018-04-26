package person;

import law.Laws;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class User extends Person {
    protected List<Laws> userLaws;

    public User(String password, String accountLogin, byte[] salt, List<Laws> userLaws) {
        super(password, accountLogin, salt);
        this.userLaws = userLaws;
    }

    public List<Laws> getUserLaws() {
        return userLaws;
    }

    public void setUserLaws(List<Laws> userLaws) {
        this.userLaws = userLaws;
    }

    public class Client {
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;

        public Client(Socket socket) {
            try {
                this.outputStream = new ObjectOutputStream(socket.getOutputStream());
                this.inputStream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ObjectInputStream getInputStream() {
            return inputStream;
        }

        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }
    }
}
