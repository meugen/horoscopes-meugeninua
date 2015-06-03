package ua.meugen.horoscopes.actions.dto;

/**
 * Created by admin on 03.06.2015.
 */
public final class NameDto {

    private int id;
    private int sex;
    private String name;

    /**
     * Getter for id.
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id Id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter for sex.
     * @return Sex
     */
    public int getSex() {
        return sex;
    }

    /**
     * Setter for sex.
     * @param sex Sex
     */
    public void setSex(final int sex) {
        this.sex = sex;
    }

    /**
     * Getter for name.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     * @param name Name
     */
    public void setName(final String name) {
        this.name = name;
    }
}
