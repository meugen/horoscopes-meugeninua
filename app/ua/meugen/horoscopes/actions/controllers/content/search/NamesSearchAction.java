package ua.meugen.horoscopes.actions.controllers.content.search;

import ua.meugen.horoscopes.actions.dto.NameDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 03.06.2015.
 */
public final class NamesSearchAction extends AbstractInterpretationSearchAction<NameDto> {

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
    protected NameDto fetchDto(final ResultSet resultSet) throws SQLException {
        final NameDto nameDto = new NameDto();
        nameDto.setId(resultSet.getInt(1));
        nameDto.setSex(resultSet.getInt(2));
        nameDto.setName(resultSet.getString(3));
        return nameDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ItemsResponse<NameDto> newResponse() {
        return new ItemsResponse<>();
    }
}
