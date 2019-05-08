package ua.meugen.horoscopes.utils;

import io.ebean.ExpressionList;
import ua.meugen.horoscopes.actions.requests.BaseHoroscopesRequest;
import ua.meugen.horoscopes.entities.Horoscope;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class HoroscopeUtils {

    private static final Set<String> RU_LOCALES = new HashSet<>(Arrays.asList("ru", "uk", "be", "bg", "az",
            "lv", "lt", "et", "hy", "kk", "ky", "mo"));

    public static final String DEFAULT_LOCALE = "ru";
    public static final String DEFAULT_KIND = "common";
    public static final List<String> WIDGET_PERIODS =  Arrays.asList("today", "cur", "second");

    private HoroscopeUtils() {
        // Nothing to do
    }

    public static String getLocale(final BaseHoroscopesRequest request) {
        String locale = request.getLocale();
        locale = RU_LOCALES.contains(locale) ? "ru" : "en";
        return locale;
    }

    public static ExpressionList<Horoscope> bindPeriods(
            final ExpressionList<Horoscope> expressions, final List<String> periods) {
        if (periods != null && !periods.isEmpty()) {
            expressions.in("period", periods);
        }
        return expressions;
    }
}
