package controllers.content.get;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.db.DB;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meugen on 24.06.14.
 */
public final class Name extends Controller {

    private static final Log LOG = LogFactory.getLog(Name.class);

    private static final String GET_NAME_SQL = "select content from horo_names_v2 where upname=?";
    private static final String NAME_PARAM = "name";
    private static final String TEXT_KEY = "text";

    public static Result index() {
        Result result;
        try {
            final JsonNode json = request().body().asJson();
            if (json == null) {
                result = badRequest();
            } else {
                result = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                    public Result onAction(PreparedStatement statement) throws SQLException {
                        statement.setString(1, json.get(NAME_PARAM).textValue().toUpperCase());
                        return ok(Response.content(fetchContent(statement.executeQuery())).asJson());
                    }
                }, GET_NAME_SQL);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(Response.error(e).asJson());
        }
        return result;
    }

    private static JsonNode fetchContent(final ResultSet resultSet) throws SQLException {
        JsonNode content = NullNode.getInstance();
        if (resultSet.next()) {
            final ObjectNode object = Json.newObject();
            object.put(TEXT_KEY, resultSet.getString(1));
            content = object;
        }
        return content;
    }
}
