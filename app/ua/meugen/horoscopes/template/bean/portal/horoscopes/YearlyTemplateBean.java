package ua.meugen.horoscopes.template.bean.portal.horoscopes;

import play.i18n.Lang;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class YearlyTemplateBean extends IndexTemplateBean {

    public YearlyTemplateBean() {
        super("horoscopes-yearly");
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
        return messages.get(Lang.defaultLang(), "horoscopes.yearly.title");
    }
}
