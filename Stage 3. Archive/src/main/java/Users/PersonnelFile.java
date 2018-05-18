package Users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PersonnelFile implements Serializable {
    BasicInformation basicInformation;
    ContactInformation contactInformation;
    List<Job> jobs;

    public PersonnelFile() {
        basicInformation = new BasicInformation();
        contactInformation = new ContactInformation();
    }

    public PersonnelFile(String firstName, String middleName, String lastName, String gender, String birthDate, String passport,
                         String country, String city, String street, String house, String mobilePhone, String homePhone) {
        basicInformation = new BasicInformation(firstName, middleName, lastName, gender, birthDate, passport);
        contactInformation = new ContactInformation(country, city, street, house, mobilePhone, homePhone);
    }

    public class BasicInformation implements Serializable {
        private String firstName;
        private String middleName;
        private String lastName;
        private String gender;
        private String birthDate;
        private String passport;

        BasicInformation(String firstName, String middleName, String lastName, String gender, String birthDate, String passport) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.gender = gender;
            this.birthDate = birthDate;
            this.passport = passport;
        }

        public BasicInformation() {

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

        public String getPassport() {
            return passport;
        }

        public void setPassport(String passport) {
            this.passport = passport;
        }

        public String getDate() {
            return birthDate;
        }

        public void setDate(String birthDate) {
            this.birthDate = birthDate;
        }
    }

    public class ContactInformation implements Serializable {
        private String country;
        private String city;
        private String street;
        private String house;
        private String mobilePhone;
        private String homePhone;

        ContactInformation(String country, String city, String street, String house, String mobilePhone, String homePhone) {
            this.country = country;
            this.city = city;
            this.street = street;
            this.house = house;
            this.mobilePhone = mobilePhone;
            this.homePhone = homePhone;
        }

        public ContactInformation() {

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

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
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

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
