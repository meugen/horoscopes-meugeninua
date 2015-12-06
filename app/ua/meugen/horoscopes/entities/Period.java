package ua.meugen.horoscopes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "periods")
public class Period implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Column(nullable = false, length = 10)
    private String type;
    @Column(nullable = false, length = 30)
    private String period;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(final String period) {
        this.period = period;
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
