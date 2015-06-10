package ua.meugen.horoscopes.configs;

import akka.actor.ActorSystem;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateAllAction;

import java.util.concurrent.TimeUnit;

/**
 * Global settings.
 *
 * @author meugen
 */
public final class Global extends GlobalSettings {

    @Inject
    private UpdateAllAction updateAllAction;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(final Application app) {
        super.onStart(app);

        DateTime daily = new DateTime().withHourOfDay(1)
                .withMinuteOfHour(0).withSecondOfMinute(0)
                .withMillisOfSecond(0);
        while (daily.isBeforeNow()) {
            daily = daily.plusDays(1);
        }
        final int dailyDelayInSeconds = Seconds.secondsBetween(DateTime.now(), daily).getSeconds();

        final Runnable runnable = () -> this.updateAllAction.execute("akka /content/update/all");
        final ActorSystem system = Akka.system();
        system.scheduler().schedule(
                Duration.create(dailyDelayInSeconds, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.DAYS),
                runnable, system.dispatcher());
    }
}
