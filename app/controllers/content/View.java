package controllers.content;

import helpers.DatabaseHelper;
import models.MessageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.api.UsefulException;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by meugen on 02.07.14.
 */
public final class View extends Controller {

    private static final Log LOG = LogFactory.getLog(View.class);

    private static final String GET_MESSAGES_SQL = "select id, title, message from horo_messages";
    private static final String GET_MESSAGE_SQL = "select id, title, message from horo_messages where id=?";
    private static final int MESSAGE_LENGTH = 128;

    public static Result messages() {
        Result result;
        try {
            final List<MessageInfo> messages = DatabaseHelper.actionWithStatement(
                    new DatabaseHelper.StatementAction<List<MessageInfo>>() {
                        public List<MessageInfo> onAction(PreparedStatement statement) throws SQLException {
                            return fetchMessages(statement);
                        }
                    }, GET_MESSAGES_SQL);
            result = ok(views.html.viewmessages.render(messages));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            result = internalServerError(views.html.defaultpages.error.render(new UsefulException(e.getMessage(), e) {
            }));
        }
        return result;
    }

    public static Result message(final Integer id) {
        Result result;
        try {
            final MessageInfo message = DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<MessageInfo>() {
                public MessageInfo onAction(final PreparedStatement statement) throws SQLException {
                    return fetchMessage(statement, id);
                }
            }, GET_MESSAGE_SQL);
            result = message == null ? ok(views.html.viewnomessage.render())
                    : ok(views.html.viewmessage.render(message));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            result = internalServerError(views.html.defaultpages.error.render(new UsefulException(e.getMessage(), e) {
            }));
        }
        return result;
    }

    private static List<MessageInfo> fetchMessages(final PreparedStatement statement) throws SQLException {
        final ResultSet resultSet = statement.executeQuery();
        final String defTitle = Messages.get("message.info.no-title");

        final List<MessageInfo> messages = new LinkedList<>();
        while (resultSet.next()) {
            final MessageInfo message = new MessageInfo();
            message.setId(resultSet.getInt(1));
            message.setTitle(defaultString(resultSet.getString(2), defTitle));
            message.setMessage(StringUtils.abbreviate(resultSet.getString(3), MESSAGE_LENGTH));
            messages.add(message);
        }
        return messages;
    }

    private static MessageInfo fetchMessage(final PreparedStatement statement, final Integer id) throws SQLException {
        statement.setInt(1, id);
        final ResultSet resultSet = statement.executeQuery();

        MessageInfo message = null;
        if (resultSet.next()) {
            message = new MessageInfo();
            message.setId(resultSet.getInt(1));
            message.setTitle(defaultString(resultSet.getString(2), Messages.get("message.info.no-title")));
            message.setMessage(resultSet.getString(3));
        }
        return message;
    }

    private static String defaultString(final String value, final String def) {
        return value == null || value.length() == 0 ? def : value;
    }
}
