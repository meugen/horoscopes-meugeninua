package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.controllers.Application;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.DailyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.MonthlyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.WeeklyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.YearlyTemplateBean;
import views.html.horoscopes.daily;
import views.html.horoscopes.monthly;
import views.html.horoscopes.weekly;
import views.html.horoscopes.yearly;

import javax.inject.Inject;

public final class Horoscopes {

    @Inject
    private DailyTemplateBean dailyTemplateBean;

    @Inject
    private WeeklyTemplateBean weeklyTemplateBean;

    @Inject
    private MonthlyTemplateBean monthlyTemplateBean;

    @Inject
    private YearlyTemplateBean yearlyTemplateBean;

    public F.Promise<Result> daily() {
        return F.Promise.promise(() -> Application.ok(daily.render(this.dailyTemplateBean)));
    }

    public F.Promise<Result> weekly() {
        return F.Promise.promise(() -> Application.ok(weekly.render(this.weeklyTemplateBean)));
    }

    public F.Promise<Result> monthly() {
        return F.Promise.promise(() -> Application.ok(monthly.render(this.monthlyTemplateBean)));
    }

    public F.Promise<Result> yearly() {
        return F.Promise.promise(() -> Application.ok(yearly.render(this.yearlyTemplateBean)));
    }

}
