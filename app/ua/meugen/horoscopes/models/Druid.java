package ua.meugen.horoscopes.models;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "druids")
public class Druid extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Column(nullable = false, length = 100)
    private String druid;
    @Column(nullable = false, length = 100)
    private String updruid;
    @ManyToOne
    @JoinColumn(name = "icon_id", nullable = false)
    private Upload icon;
    @Column(nullable = false, length = 100)
    private String period;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false)
    private Integer sort;
    @Column(nullable = false, length = 10)
    private String locale;
    @Column(nullable = false)
    private UUID guid = UUID.randomUUID();

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getDruid() {
        return druid;
    }

    public void setDruid(final String druid) {
        this.druid = druid;
    }

    public String getUpdruid() {
        return updruid;
    }

    public void setUpdruid(final String updruid) {
        this.updruid = updruid;
    }

    public Upload getIcon() {
        return icon;
    }

    public void setIcon(final Upload icon) {
        this.icon = icon;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(final String period) {
        this.period = period;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(final Integer sort) {
        this.sort = sort;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
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
        final Druid druid = (Druid) o;
        return Objects.equals(guid, druid.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
