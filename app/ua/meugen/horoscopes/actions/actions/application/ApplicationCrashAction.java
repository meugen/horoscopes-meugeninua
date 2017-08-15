package ua.meugen.horoscopes.actions.actions.application;

import com.fasterxml.jackson.databind.JsonNode;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import play.Logger;
import play.i18n.Messages;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public final class ApplicationCrashAction {

    private static final Logger.ALogger LOG = Logger.of(ApplicationCrashAction.class);

    private static final String USER_KEY = "uY88LgsdcrA9kYCMDBLYNNpGmijPuf";
    private static final String APP_TOKEN = "ah7JwRJZ5CY8a4VtfKUkpttUA6kCRH";

    private static ExecutorService SERVICE;

    @Inject
    private WSClient wsClient;

    public CompletionStage<Result> execute(final JsonNode json) {
        if (SERVICE == null) {
            SERVICE = Executors.newSingleThreadScheduledExecutor();
        }
        SERVICE.execute(new PushoverRunnable());
        return this.wsClient.url("http://127.0.0.1:5984/acra-horoscopes/_design/acra-storage/_update/report")
                .put(json).thenApply((wsResponse) -> Controller.ok(wsResponse.getBody()));
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
