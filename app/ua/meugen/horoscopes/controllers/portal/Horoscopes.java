package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.DailyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.MonthlyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.WeeklyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.YearlyTemplateBean;
import views.html.horoscopes.daily;

import javax.inject.Inject;

public final class Horoscopes extends Controller {

    @Inject
    private DailyTemplateBean dailyTemplateBean;

    @Inject
    private WeeklyTemplateBean weeklyTemplateBean;

    @Inject
    private MonthlyTemplateBean monthlyTemplateBean;

    @Inject
    private YearlyTemplateBean yearlyTemplateBean;

    public F.Promise<Result> daily() {
        return F.Promise.promise(() -> ok(daily.render(this.dailyTemplateBean)));
    }

}
