package ua.meugen.horoscopes.template.bean.portal.horoscopes;

import play.i18n.Messages;
import ua.meugen.horoscopes.template.bean.IndexTemplateBean;

public final class DailyTemplateBean extends IndexTemplateBean {

    public DailyTemplateBean() {
        super("horoscopes-daily");
    }

    public String yesterdayTitle() {
        return "Yesterday title";
    }

    public String yesterdayContent() {
        return "Yesterday content";
    }

    public String todayTitle() {
        return "Today title";
    }

    public String todayContent() {
        return "Today content";
    }

    public String tomorrowTitle() {
        return "Tomorrow title";
    }

    public String tomorrowContent() {
        return "Tomorrow content";
    }

    public String tomorrow02Title() {
        return "Day after tomorrow title";
    }

    public String tomorrow02Content() {
        return "Day after tomorrow content";
    }

    @Override
    public String title() {
        return Messages.get("horoscopes.daily.title");
    }
}
