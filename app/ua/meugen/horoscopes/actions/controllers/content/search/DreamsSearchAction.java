package ua.meugen.horoscopes.actions.controllers.content.search;

import ua.meugen.horoscopes.actions.dto.DreamItemDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 03.06.2015.
 */
public final class DreamsSearchAction extends AbstractInterpretationSearchAction<DreamItemDto> {

    private static final String SQL = "select id, type, dream from horo_dreams_v2 where" +
            " updream like concat(?, '%') and locale=? order by type, dream";

    public DreamsSearchAction() {
        super(SQL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DreamItemDto fetchDto(final ResultSet resultSet) throws SQLException {
        final DreamItemDto dreamDto = new DreamItemDto();
        dreamDto.setId(resultSet.getInt(1));
        dreamDto.setType(resultSet.getInt(2));
        dreamDto.setDream(resultSet.getString(3));
        return dreamDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ItemsResponse<DreamItemDto> newResponse() {
        return new ItemsResponse<>();
    }
}
