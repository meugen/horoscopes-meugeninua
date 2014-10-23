package helpers.controllers.application;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.controllers.ControllerHelper;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import play.i18n.Messages;
import play.libs.F;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 23.10.2014.
 */
public final class ApplicationCrashHelper implements ControllerHelper {

    private static final Log LOG = LogFactory.getLog(ApplicationCrashHelper.class);

    private static final ExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final String USER_KEY = "uY88LgsdcrA9kYCMDBLYNNpGmijPuf";
    private static final String APP_TOKEN = "ah7JwRJZ5CY8a4VtfKUkpttUA6kCRH";

    private final JsonNode json;

    /**
     * Constructor.
     * @param json Json
     */
    public ApplicationCrashHelper(JsonNode json) {
        this.json = json;
    }

    /**
     * {@inheritDoc}
     */
    public F.Promise<Result> execute() {
        SERVICE.execute(new PushoverRunnable());
        return WS.url("http://127.0.0.1:5984/acra-horoscopes/_design/acra-storage/_update/report")
                .put(this.json).map(new F.Function<WSResponse, Result>() {
                    public Result apply(WSResponse wsResponse) throws Throwable {
                        return Controller.ok(wsResponse.getBody());
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
