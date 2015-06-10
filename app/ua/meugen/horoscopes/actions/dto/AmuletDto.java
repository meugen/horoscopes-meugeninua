package ua.meugen.horoscopes.actions.dto;

/**
 * Created by admin on 03.06.2015.
 */
public final class AmuletDto {

    private int id;
    private int type;
    private String amulet;

    /**
     * Getter for id.
     *
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id.
     *
     * @param id Id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Getter for type.
     *
     * @return Type
     */
    public int getType() {
        return type;
    }

    /**
     * Setter for type.
     *
     * @param type Type
     */
    public void setType(final int type) {
        this.type = type;
    }

    /**
     * Getter for amulet.
     *
     * @return Amulet
     */
    public String getAmulet() {
        return amulet;
    }

    /**
     * Setter for amulet.
     *
     * @param amulet Amulet
     */
    public void setAmulet(final String amulet) {
        this.amulet = amulet;
    }
}
