package ua.meugen.horoscopes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "dreams")
public class Dream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Column(nullable = false, length = 100)
    private String dream;
    @Column(nullable = false, length = 100)
    private String updream;
    @Column(nullable = false)
    private Integer type;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false, length = 10)
    private String locale;
    @Column(nullable = false, length = 100)
    private String rusDream;
    @Column(nullable = false)
    private UUID guid = UUID.randomUUID();

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getDream() {
        return dream;
    }

    public void setDream(final String dream) {
        this.dream = dream;
    }

    public String getUpdream() {
        return updream;
    }

    public void setUpdream(final String updream) {
        this.updream = updream;
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

    public String getRusDream() {
        return rusDream;
    }

    public void setRusDream(final String rusDream) {
        this.rusDream = rusDream;
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
        final Dream dream = (Dream) o;
        return Objects.equals(guid, dream.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
