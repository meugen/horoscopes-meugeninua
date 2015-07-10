package ua.meugen.horoscopes.configs;

import akka.actor.ActorSystem;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import play.inject.Injector;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateAllAction;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * Configure updates.
 * @author meugen
 */
final class UpdatesConfig implements Config {

    @Inject
    private ActorSystem system;

    @Inject
    private UpdateAllAction updateAllAction;

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure() {
        DateTime daily = new DateTime().withHourOfDay(1)
                .withMinuteOfHour(0).withSecondOfMinute(0)
                .withMillisOfSecond(0);
        while (daily.isBeforeNow()) {
            daily = daily.plusDays(1);
        }
        final int dailyDelayInSeconds = Seconds.secondsBetween(DateTime.now(), daily).getSeconds();

        final Runnable runnable = () -> this.updateAllAction.execute("akka /content/update/all");
        this.system.scheduler().schedule(
                Duration.create(dailyDelayInSeconds, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.DAYS),
                runnable, this.system.dispatcher());
    }
}
