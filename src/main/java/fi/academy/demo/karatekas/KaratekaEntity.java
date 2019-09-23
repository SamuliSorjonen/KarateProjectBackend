package fi.academy.demo.karatekas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.demo.karatestyles.KarateStyleEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class KaratekaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date birthday;
    private String nationality;
    private String belt;
    private String description;
    @Column(length = 8000)
    private String story;

    @OneToMany(mappedBy = "founder")
    @JsonIgnoreProperties({"founder", "practitioners"})
    private List<KarateStyleEntity> founderOf;

    @ManyToMany
    @JoinTable(name="joiningtable_style_karateka",
            joinColumns = @JoinColumn(name="karateka_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="style_id", referencedColumnName = "id"))

    @JsonIgnoreProperties("practitioners")
    private List<KarateStyleEntity> tradition;

    public KaratekaEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBelt() {
        return belt;
    }

    public void setBelt(String belt) {
        this.belt = belt;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public List<KarateStyleEntity> getTradition() { return tradition; }

    public void setTradition(List<KarateStyleEntity> tradition) { this.tradition = tradition; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<KarateStyleEntity> getFounderOf() { return founderOf; }

    public void setFounderOf(List<KarateStyleEntity> founderOf) { this.founderOf = founderOf; }
}
