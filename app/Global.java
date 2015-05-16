import akka.actor.ActorSystem;
import akka.actor.Scheduler;
import helpers.controllers.content.update.UpdateHelpersFactory;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import runnables.ControllerHelperRunnable;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Global settings.
 * @author meugen
 */
public final class Global extends GlobalSettings {

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

        final ActorSystem system = Akka.system();
        system.scheduler().schedule(
                Duration.create(dailyDelayInSeconds, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.DAYS),
                new ControllerHelperRunnable(UpdateHelpersFactory.newUpdateAllHelper("akka /content/update/all")),
                system.dispatcher());
    }
}
