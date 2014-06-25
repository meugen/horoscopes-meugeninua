package controllers.content.add;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.content.Response;
import helpers.DatabaseHelper;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.*;
import java.util.Map;

/**
 * Created by meugen on 25.06.14.
 */
public final class Message extends Controller {

    private static final Log LOG = LogFactory.getLog(Message.class);

    private static final String USER_KEY = "uY88LgsdcrA9kYCMDBLYNNpGmijPuf";
    private static final String APP_TOKEN = "ah7JwRJZ5CY8a4VtfKUkpttUA6kCRH";

    private static final String ADD_MESSAGE_SQL = "insert into horo_messages (title, message) values (?, ?)";

    public static Result index() {
        Result result;
        try {
            final Map<String, String[]> params = request().body().asFormUrlEncoded();
            final int messageId = DatabaseHelper.actionWithDatabase(new DatabaseHelper.ConnectionAction<Integer>() {
                public Integer onAction(final Connection connection) throws SQLException {
                    return insertMessage(connection, params);
                }
            });

            final PushoverMessage pushoverMessage = PushoverMessage.builderWithApiToken(APP_TOKEN)
                    .setUserId(USER_KEY)
                    .setMessage("Получено новое сообщение.")
                    .setUrl("http://horoscopes.meugen.in.ua/content/view/message/" + messageId)
                    .build();
            final net.pushover.client.Status status = new PushoverRestClient().pushMessage(pushoverMessage);
            result = ok(status.getStatus() == 1 ? createResponse("", Response.Status.OK)
                    : createResponse("Error sending push message.", Response.Status.ERROR));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            result = ok(createResponse(e.getMessage(), Response.Status.ERROR));
        }
        return result;
    }

    private static JsonNode createResponse(final String message, final Response.Status status) {
        final ObjectNode result = Json.newObject();
        result.put("message", message);
        result.put("status", status.name());
        return result;
    }

    private static int insertMessage(final Connection connection, final Map<String, String[]> params) throws SQLException {
        connection.setAutoCommit(false);

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(ADD_MESSAGE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, params.get("title")[0]);
            statement.setString(2, params.get("message")[0]);
            statement.executeUpdate();

            final ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            final int result = resultSet.getInt(1);
            connection.commit();

            return result;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
