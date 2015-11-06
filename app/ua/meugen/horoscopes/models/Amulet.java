package ua.meugen.horoscopes.models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by meugen on 25.10.15.
 */
@Entity
@Table(name = "horo_amulets")
public class Amulet extends Model {

    @Id
    private Integer id;
    @Column(nullable = false)
    private String upamulet;
    @Column(nullable = false)
    private String amulet;
    @ManyToOne
    @JoinColumn(name = "image_id")
    private Upload image;
    @JoinColumn(nullable = false)
    private Integer type;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String locale;
    @Column(name = "rus_amulet", nullable = false)
    private String rusAmulet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpamulet() {
        return upamulet;
    }

    public void setUpamulet(String upamulet) {
        this.upamulet = upamulet;
    }

    public String getAmulet() {
        return amulet;
    }

    public void setAmulet(String amulet) {
        this.amulet = amulet;
    }

    public Upload getImage() {
        return image;
    }

    public void setImage(Upload image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRusAmulet() {
        return rusAmulet;
    }

    public void setRusAmulet(String rusAmulet) {
        this.rusAmulet = rusAmulet;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amulet amulet = (Amulet) o;
        return Objects.equals(id, amulet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
