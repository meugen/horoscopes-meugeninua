package ua.meugen.horoscopes.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import play.i18n.Messages;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public final class MapsModule extends AbstractModule {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        // Nothing to do
    }

    @Provides @Named("daily-kinds")
    public Map<String, String> provideDailyKinds() {
        final Map<String, String> result = new HashMap<>();
        result.put("common", "http://img.ignio.com/r/export/utf/xml/daily/com.xml");
        result.put("erotic", "http://img.ignio.com/r/export/utf/xml/daily/ero.xml");
        result.put("anti", "http://img.ignio.com/r/export/utf/xml/daily/anti.xml");
        result.put("business", "http://img.ignio.com/r/export/utf/xml/daily/bus.xml");
        result.put("health", "http://img.ignio.com/r/export/utf/xml/daily/hea.xml");
        result.put("cook", "http://img.ignio.com/r/export/utf/xml/daily/cook.xml");
        result.put("love", "http://img.ignio.com/r/export/utf/xml/daily/lov.xml");
        result.put("mobile", "http://img.ignio.com/r/export/utf/xml/daily/mob.xml");
        return result;
    }
    
    @Provides @Named("monday-weekly-periods")
    public Map<String, Object> provideMondayWeeklyPeriods() {
        final Map<String, Object> result = new HashMap<>();
        result.put("next", -1);
        result.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        result.put("prev", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");
        return result;
    }
    
    @Provides @Named("saturday-weekly-periods")
    public Map<String, Object> provideSaturdayWeeklyPeriods() {
        final Map<String, Object> result = new HashMap<>();
        result.put("aaa", -1);
        result.put("next", "http://img.ignio.com/r/export/utf/xml/weekly/cur.xml");
        result.put("cur", "http://img.ignio.com/r/export/utf/xml/weekly/prev.xml");
        result.put("prev", 0);
        return result;
    }

    @Provides @Named("month-codes")
    public Map<String, Integer> provideMonthCodes(@Named("ru") final Messages messages) {
        final Map<String, Integer> result = new HashMap<>();
        result.put(messages.at("month.january"), Calendar.JANUARY);
        result.put(messages.at("month.february"), Calendar.FEBRUARY);
        result.put(messages.at("month.march"), Calendar.MARCH);
        result.put(messages.at("month.april"), Calendar.APRIL);
        result.put(messages.at("month.may"), Calendar.MAY);
        result.put(messages.at("month.june"), Calendar.JUNE);
        result.put(messages.at("month.july"), Calendar.JULY);
        result.put(messages.at("month.august"), Calendar.AUGUST);
        result.put(messages.at("month.september"), Calendar.SEPTEMBER);
        result.put(messages.at("month.october"), Calendar.OCTOBER);
        result.put(messages.at("month.november"), Calendar.NOVEMBER);
        result.put(messages.at("month.december"), Calendar.DECEMBER);
        return result;
    }
}
