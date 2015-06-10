package ua.meugen.horoscopes.actions.controllers.content.get;

import ua.meugen.horoscopes.actions.dto.BaseContentDto;
import ua.meugen.horoscopes.actions.responses.ContentResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 10.06.15.
 */
public final class SimpleGetByIdAction extends AbstractGetByIdAction<BaseContentDto> {

    /**
     * Constructor.
     * @param sql SQL
     */
    public SimpleGetByIdAction(final String sql) {
        super(sql);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContentResponse<BaseContentDto> newResponse() {
        return new ContentResponse<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BaseContentDto fetchDto(final ResultSet resultSet) throws SQLException {
        final BaseContentDto contentDto = new BaseContentDto();
        contentDto.setText(resultSet.getString(1));
        return contentDto;
    }
}
