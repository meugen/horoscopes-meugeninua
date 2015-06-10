package ua.meugen.horoscopes.actions.controllers.content.search;

import ua.meugen.horoscopes.actions.dto.NameItemDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 03.06.2015.
 */
public final class NamesSearchAction extends AbstractInterpretationSearchAction<NameItemDto> {

    private static final String SQL = "select id, sex, name from horo_names_v2 where" +
            " upname like concat(?, '%') and locale=? order by sex, name";

    /**
     * Default constructor.
     */
    public NamesSearchAction() {
        super(SQL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected NameItemDto fetchDto(final ResultSet resultSet) throws SQLException {
        final NameItemDto nameDto = new NameItemDto();
        nameDto.setId(resultSet.getInt(1));
        nameDto.setSex(resultSet.getInt(2));
        nameDto.setName(resultSet.getString(3));
        return nameDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ItemsResponse<NameItemDto> newResponse() {
        return new ItemsResponse<>();
    }
}
