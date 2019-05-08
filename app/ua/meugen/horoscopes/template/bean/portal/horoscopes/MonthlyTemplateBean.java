package ua.meugen.horoscopes.template.bean.portal.horoscopes;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class MonthlyTemplateBean extends IndexTemplateBean {

    public MonthlyTemplateBean() {
        super("horoscopes-monthly");
    }

    public String firstTitle() {
        return "First month title";
    }

    public String firstContent() {
        return "First month content";
    }

    public String secondTitle() {
        return "Second month title";
    }

    public String secondContent() {
        return "Second month content";
    }

    @Override
    public String title() {
        return messages.get(Lang.defaultLang(), "horoscopes.monthly.title");
    }
}
