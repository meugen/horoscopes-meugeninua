package ua.meugen.horoscopes.actions.controllers.content;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 23.10.14.
 */
public interface OnFillObjectListener {

    /**
     * Call when need to fill object.
     *
     * @param object    Object
     * @param resultSet Result set
     * @throws SQLException On some sql error
     */
    void onFillObject(ObjectNode object, ResultSet resultSet) throws SQLException;
}
