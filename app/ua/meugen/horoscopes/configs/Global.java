package ua.meugen.horoscopes.configs;

import akka.actor.ActorSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.meugen.horoscopes.actions.controllers.content.update.UpdateAllAction;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Global settings.
 *
 * @author meugen
 */
public final class Global extends GlobalSettings {

    private static ApplicationContext ctx;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart(final Application app) {
        super.onStart(app);
        ctx = new AnnotationConfigApplicationContext("ua.meugen.horoscopes");

        DateTime daily = new DateTime().withHourOfDay(1)
                .withMinuteOfHour(0).withSecondOfMinute(0)
                .withMillisOfSecond(0);
        while (daily.isBeforeNow()) {
            daily = daily.plusDays(1);
        }
        final int dailyDelayInSeconds = Seconds.secondsBetween(DateTime.now(), daily).getSeconds();

        final UpdateAllAction action = new UpdateAllAction();
        action.setUri("akka /content/update/all");
        final Runnable runnable = action::execute;
        final ActorSystem system = Akka.system();
        system.scheduler().schedule(
                Duration.create(dailyDelayInSeconds, TimeUnit.SECONDS),
                Duration.create(1, TimeUnit.DAYS),
                runnable, system.dispatcher());
    }

    /**
     * {@inheritDoc}
     */
    public <A> A getControllerInstance(final Class<A> controllerClass) throws Exception {
        return ctx.getBean(controllerClass);
    }

    public static ApplicationContext context() {
        return ctx;
    }
}
