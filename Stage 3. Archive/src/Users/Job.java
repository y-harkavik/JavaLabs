package Users;

import java.io.Serializable;

public class Job implements Serializable {
    private String company;
    private String position;
    private Integer experience;

    public Job(String company, String position, Integer experience) {
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

    public Integer getExperience() {
        return experience;
    }
}
