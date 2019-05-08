package ua.meugen.horoscopes.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@NamedQueries({
        @NamedQuery(name = Period.PERIOD_BY_TYPE, query = "find Period where type=:type and name=:period"),
        @NamedQuery(name = Period.PERIOD_DELETE_OLD_PERIODS, query = "delete from Period where type=:type and name=:period")
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
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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
