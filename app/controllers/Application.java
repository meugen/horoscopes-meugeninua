package controllers;

import helpers.DatabaseHelper;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.i18n.Messages;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application extends Controller {

    private static final Log LOG = LogFactory.getLog(Application.class);

    private static final ExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final String USER_KEY = "uY88LgsdcrA9kYCMDBLYNNpGmijPuf";
    private static final String APP_TOKEN = "ah7JwRJZ5CY8a4VtfKUkpttUA6kCRH";

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result main() {
        try {
            return DatabaseHelper.actionWithStatement(new DatabaseHelper.StatementAction<Result>() {
                public Result onAction(PreparedStatement statement) throws SQLException {
                    return doMain(statement);
                }
            }, "SELECT name FROM horo_names_v2 where sex=? order by upname");
        } catch (SQLException e) {
            return internalServerError(e.getMessage());
        }
    }

    private static Result doMain(final PreparedStatement statement) throws SQLException {
        statement.setInt(1, 1);
        final ResultSet result = statement.executeQuery();

        final StringBuilder builder = new StringBuilder();
        while (result.next()) {
            if (builder.length() > 0) {
                builder.append(", ");
            }
            builder.append(result.getString(1));
        }
        return ok(builder.toString());
    }

    // http://192.168.0.100:5984/acra-myapp/_design/acra-storage/_update/report
    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> crash() {
        SERVICE.execute(new PushoverRunnable());
        return WS.url("http://127.0.0.1:5984/acra-horoscopes/_design/acra-storage/_update/report")
                .put(request().body().asJson()).map(new F.Function<WSResponse, Result>() {
                    @Override
                    public Result apply(WSResponse wsResponse) throws Throwable {
                        return ok(wsResponse.getBody());
                    }
                });
    }

    public static final class PushoverRunnable implements Runnable {

        @Override
        public void run() {
            try {
                final PushoverMessage pushoverMessage = PushoverMessage.builderWithApiToken(APP_TOKEN)
                        .setUserId(USER_KEY)
                        .setMessage(Messages.get("got.new.error.message"))
                        .build();
                new PushoverRestClient().pushMessage(pushoverMessage);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
