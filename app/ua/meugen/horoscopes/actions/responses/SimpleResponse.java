package ua.meugen.horoscopes.actions.responses;

/**
 * Created by meugen on 08.06.15.
 */
public class SimpleResponse extends BaseResponse {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
