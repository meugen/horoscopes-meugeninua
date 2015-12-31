package ua.meugen.horoscopes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "updates")
public class Update implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    @Column(name = "update_date", nullable = false)
    private Date date = new Date();
    @Column(nullable = false)
    private String uri;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String respone;
    @Column(nullable = false)
    private UUID guid = UUID.randomUUID();

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getRespone() {
        return respone;
    }

    public void setRespone(final String respone) {
        this.respone = respone;
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
        final Update update = (Update) o;
        return Objects.equals(guid, update.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
