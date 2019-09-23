package fi.academy.demo.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.demo.karatestyles.KarateStyleEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String username;
    private String password;
    private String email;
    private String belt;
    private String trainingPlans;
    @ManyToMany
    @JoinTable(name="joiningtable_style_user",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name="style_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("practitioners")
    private List<KarateStyleEntity> karateStyles;


    public UserEntity() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBelt() { return belt; }

    public void setBelt(String belt) { this.belt = belt; }

    public String getTrainingPlans() { return trainingPlans; }

    public void setTrainingPlans(String trainingPlans) { this.trainingPlans = trainingPlans; }

    public List<KarateStyleEntity> getKarateStyles() { return karateStyles; }

    public void setKarateStyles(List<KarateStyleEntity> karateStyles) { this.karateStyles = karateStyles; }
}
