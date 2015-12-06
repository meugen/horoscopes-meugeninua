package ua.meugen.horoscopes.entities;

import com.avaje.ebean.annotation.NamedUpdate;
import com.avaje.ebean.annotation.NamedUpdates;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(name = Period.PERIOD_BY_TYPE, query = "find Period where type=:type and value=:period")
})
@NamedUpdates({
        @NamedUpdate(name = Period.PERIOD_DELETE_OLD_PERIODS, update = "delete from Period where type=:type and value=:period")
})
@Entity
@Table(name = "periods")
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PERIOD_BY_TYPE = "period.by-type";
    public static final String PERIOD_DELETE_OLD_PERIODS = "period.delete-old-periods";

    @Id
    private Integer id;
    @Column(nullable = false, length = 10)
    private String type;
    @Column(nullable = false, length = 30)
    private String value;
    @Column(nullable = false, length = 30)
    private String key;
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

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
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
        final Period period = (Period) o;
        return Objects.equals(guid, period.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
