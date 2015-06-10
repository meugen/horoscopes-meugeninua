package ua.meugen.horoscopes.actions.controllers.content.search;

import ua.meugen.horoscopes.actions.dto.AmuletItemDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 03.06.2015.
 */
public final class AmuletsSearchAction extends AbstractInterpretationSearchAction<AmuletItemDto> {

    private static final String SQL = "select id, type, amulet from horo_amulets_v2 where" +
            " upamulet like concat(?, '%') and locale=? order by type, amulet";

    /**
     * Default constructor.
     */
    public AmuletsSearchAction() {
        super(SQL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AmuletItemDto fetchDto(final ResultSet resultSet) throws SQLException {
        final AmuletItemDto amuletDto = new AmuletItemDto();
        amuletDto.setId(resultSet.getInt(1));
        amuletDto.setType(resultSet.getInt(2));
        amuletDto.setAmulet(resultSet.getString(3));
        return amuletDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ItemsResponse<AmuletItemDto> newResponse() {
        return new ItemsResponse<>();
    }
}
