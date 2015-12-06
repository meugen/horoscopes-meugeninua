package ua.meugen.horoscopes.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "amulets")
public class Amulet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Column(nullable = false, length = 100)
    private String upamulet;
    @Column(nullable = false, length = 100)
    private String amulet;
    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Upload image;
    @Column(nullable = false)
    private Integer type;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false, length = 10)
    private String locale;
    @Column(name = "rus_amulet", nullable = false, length = 100)
    private String rusAmulet;
    @Column(nullable = false)
    private UUID guid = UUID.randomUUID();

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getUpamulet() {
        return upamulet;
    }

    public void setUpamulet(final String upamulet) {
        this.upamulet = upamulet;
    }

    public String getAmulet() {
        return amulet;
    }

    public void setAmulet(final String amulet) {
        this.amulet = amulet;
    }

    public Upload getImage() {
        return image;
    }

    public void setImage(final Upload image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public String getRusAmulet() {
        return rusAmulet;
    }

    public void setRusAmulet(final String rusAmulet) {
        this.rusAmulet = rusAmulet;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(final UUID guid) {
        this.guid = guid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Amulet amulet = (Amulet) o;
        return Objects.equals(guid, amulet.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
