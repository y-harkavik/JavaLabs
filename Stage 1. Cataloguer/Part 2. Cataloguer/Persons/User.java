package Persons;

import java.time.LocalDate;

public class User extends Person {

    public static final long SIZE_OF_DATA_10MB = 10485760;

    private long addedData;

    private LocalDate lastUpdated;

    /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        this.date.set(LocalDateTime.now().format(dateTimeFormatter));*/
    public User(String password, String login, byte[] salt) {
        super(password, login, salt);
    }

    @Override
    public String toString() {
        return USER;
    }

    public long getAddedData() {
        return addedData;
    }

    public void setAddedData(long addedData) {
        this.addedData += addedData;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
