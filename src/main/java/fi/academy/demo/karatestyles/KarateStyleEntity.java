package fi.academy.demo.karatestyles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.demo.User.UserEntity;
import fi.academy.demo.karatekas.KaratekaEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class KarateStyleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    private int foundationYear;
    private String description;
    @Column(length = 8000)
    private String story;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"founderOf", "tradition"})
    private KaratekaEntity founder;

    @ManyToMany
    @JoinTable(name="joiningtable_style_karateka",
        joinColumns = @JoinColumn(name="style_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="karateka_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("tradition")
    private List<KaratekaEntity> practitioners;

    @ManyToMany
    @JoinTable(name="joiningtable_style_user",
            joinColumns = @JoinColumn(name="style_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"))
    @JsonIgnoreProperties("karateStyles")
    private List<UserEntity> users;


    public KarateStyleEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KaratekaEntity getFounder() {
        return founder;
    }

    public void setFounder(KaratekaEntity founder) {
        this.founder = founder;
    }

    public int getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(int foundationYear) {
        this.foundationYear = foundationYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getStory() { return story; }

    public void setStory(String story) { this.story = story; }

    public List<KaratekaEntity> getPractitioners() { return practitioners; }

    public void setPractitioners(List<KaratekaEntity> practitioners) { this.practitioners = practitioners; }
}
