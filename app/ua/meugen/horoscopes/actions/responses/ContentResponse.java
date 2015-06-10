package ua.meugen.horoscopes.actions.responses;

/**
 * Created by meugen on 08.06.15.
 */
public final class ContentResponse<Dto> extends BaseResponse {

    private Dto content;

    public Dto getContent() {
        return content;
    }

    public void setContent(final Dto content) {
        this.content = content;
    }
}
