package person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User extends Person{
    public static final long SIZE_OF_DATA_10MB = 10485760;

    private long addedData;


    /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date.set(LocalDateTime.now().format(dateTimeFormatter));*/
    User(String password, byte[] salt) {
        super(password, salt);
    }

    @Override
    public String toString() {
        return USER;
    }

    public long getAddedData() {
        return addedData;
    }

    public void setAddedData(long addedData) {
        this.addedData = addedData;
    }
}
