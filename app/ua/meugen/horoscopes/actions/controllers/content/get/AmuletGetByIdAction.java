package ua.meugen.horoscopes.actions.controllers.content.get;

import ua.meugen.horoscopes.actions.dto.AmuletContentDto;
import ua.meugen.horoscopes.actions.responses.ContentResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 10.06.15.
 */
public final class AmuletGetByIdAction extends AbstractGetByIdAction<AmuletContentDto> {

    private static final String GET_AMULET_BY_ID_SQL = "select t2.name, t1.content from horo_amulets_v2 t1," +
            " horo_uploads t2 where t1.image_id=t2.id and t1.id=?";

    /**
     * Default constructor.
     */
    public AmuletGetByIdAction() {
        super(GET_AMULET_BY_ID_SQL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ContentResponse<AmuletContentDto> newResponse() {
        return new ContentResponse<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AmuletContentDto fetchDto(final ResultSet resultSet) throws SQLException {
        final AmuletContentDto contentDto = new AmuletContentDto();
        contentDto.setImage(resultSet.getString(1));
        contentDto.setText(resultSet.getString(2));
        return contentDto;
    }
}
