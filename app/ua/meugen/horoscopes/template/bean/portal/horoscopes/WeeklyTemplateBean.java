package ua.meugen.horoscopes.template.bean.portal.horoscopes;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class WeeklyTemplateBean extends IndexTemplateBean {

    public WeeklyTemplateBean() {
        super("horoscopes-weekly");
    }

    public boolean hasNext() {
        return true;
    }

    public String priorTitle() {
        return "Prior week title";
    }

    public String priorContent() {
        return "Prior week content";
    }

    public String currentTitle() {
        return "Current week title";
    }

    public String currentContent() {
        return "Current week content";
    }

    public String nextTitle() {
        return "Next week title";
    }

    public String nextContent() {
        return "Next week content";
    }

    @Override
    public String title() {
        return Messages.get("horoscopes.weekly.title");
    }
}
