package ua.meugen.horoscopes.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(name = Horoscope.HOROSCOPE_COUNT, query = "find Horoscope where type=:type and kind=:kind and sign=:sign and period=:period"),
        @NamedQuery(name = Horoscope.HOROSCOPE_DELETE_OLD_PERIODS, query = "delete from Horoscope where type=:type and period=:period")
})
@Entity
@Table(name = "horoscopes")
public class Horoscope implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String HOROSCOPE_COUNT = "horoscope.count";
    public static final String HOROSCOPE_DELETE_OLD_PERIODS = "horoscope.delete-old-periods";

    @Id
    private Integer id;
    @Column(nullable = false, length = 10)
    private String type;
    @Column(nullable = false, length = 30)
    private String kind;
    @Column(nullable = false, length = 30)
    private String sign;
    @Column(nullable = false, length = 30)
    private String period;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(final String kind) {
        this.kind = kind;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(final String sign) {
        this.sign = sign;
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
        final Horoscope horoscope = (Horoscope) o;
        return Objects.equals(guid, horoscope.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
