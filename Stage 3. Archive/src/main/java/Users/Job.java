package Users;

import java.io.Serializable;

public class Job implements Serializable {
    private String company;
    private String position;
    private String experience;

    public Job(String company, String position, String experience) {
        this.company = company;
        this.position = position;
        this.experience = experience;
    }

    public String getCompany() {
        return company;
    }

    public String getPosition() {
        return position;
    }

    public String getExperience() {
        return experience;
    }
}
