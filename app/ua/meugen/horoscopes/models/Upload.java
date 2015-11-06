package ua.meugen.horoscopes.models;

import com.avaje.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by meugen on 25.10.15.
 */
@Entity
@Table(name = "horo_uploads")
public class Upload extends Model {

    @Id
    private Integer id;
    @Column
    private String name;
    @Column
    private String mime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Upload upload = (Upload) o;
        return Objects.equals(id, upload.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
