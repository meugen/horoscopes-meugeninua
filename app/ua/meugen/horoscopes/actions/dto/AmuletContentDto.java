package ua.meugen.horoscopes.actions.dto;

/**
 * Created by meugen on 10.06.15.
 */
public final class AmuletContentDto extends BaseContentDto {

    private String image;
    private String text;

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
