package ua.meugen.horoscopes.actions.controllers.content.search;

import org.springframework.stereotype.Component;
import ua.meugen.horoscopes.actions.dto.AmuletDto;
import ua.meugen.horoscopes.actions.responses.ItemsResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 03.06.2015.
 */
@Component
public final class AmuletsSearchAction extends AbstractInterpretationSearchAction<AmuletDto> {

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
    protected AmuletDto fetchDto(final ResultSet resultSet) throws SQLException {
        final AmuletDto amuletDto = new AmuletDto();
        amuletDto.setId(resultSet.getInt(1));
        amuletDto.setType(resultSet.getInt(2));
        amuletDto.setAmulet(resultSet.getString(3));
        return amuletDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ItemsResponse<AmuletDto> newResponse() {
        return new ItemsResponse<>();
    }
}
