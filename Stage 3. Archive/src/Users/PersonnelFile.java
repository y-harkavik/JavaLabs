package Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PersonnelFile {
    BasicInformation basicInformation;
    ContactInformation contactInformation;
    List<Work> works;

    class BasicInformation {
        private String firstName;
        private String middleName;
        private String lastName;
        private String gender;
        private LocalDate birthDate;
        private Integer passport;

        BasicInformation(String firstName, String middleName, String lastName, String gender, LocalDate birthDate, Integer passport) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthDate = birthDate;
            this.passport = passport;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getPassport() {
            return passport;
        }

        public void setPassport(Integer passport) {
            this.passport = passport;
        }

        public LocalDate getDate() {
            return birthDate;
        }

        public void setDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }
    }

    class ContactInformation {
        private String country;
        private String city;
        private String street;
        private Integer house;
        private String mobilePhone;
        private String homePhone;

        ContactInformation(String country, String city, String street, Integer house, String mobilePhone, String homePhone) {
            this.country = country;
            this.city = city;
            this.street = street;
            this.house = house;
            this.mobilePhone = mobilePhone;
            this.homePhone = homePhone;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public Integer getHouse() {
            return house;
        }

        public void setHouse(Integer house) {
            this.house = house;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getHomePhone() {
            return homePhone;
        }

        public void setHomePhone(String homePhone) {
            this.homePhone = homePhone;
        }
    }

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        String a = "19990912";
        dateTimeFormatter.parse(a);
        String s = dateTimeFormatter.toString();
        String text = LocalDateTime.now().format(dateTimeFormatter);
    }
}
