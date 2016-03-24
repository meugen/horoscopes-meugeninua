package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.DailyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.MonthlyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.WeeklyTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.horoscopes.YearlyTemplateBean;
import views.html.horoscopes.daily;
import views.html.horoscopes.monthly;
import views.html.horoscopes.weekly;
import views.html.horoscopes.yearly;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public final class Horoscopes {

    @Inject
    private DailyTemplateBean dailyTemplateBean;

    @Inject
    private WeeklyTemplateBean weeklyTemplateBean;

    @Inject
    private MonthlyTemplateBean monthlyTemplateBean;

    @Inject
    private YearlyTemplateBean yearlyTemplateBean;

    public CompletionStage<Result> daily() {
        return CompletableFuture.supplyAsync(() -> Results.ok(daily.render(this.dailyTemplateBean)));
    }

    public CompletionStage<Result> weekly() {
        return CompletableFuture.supplyAsync(() -> Results.ok(weekly.render(this.weeklyTemplateBean)));
    }

    public CompletionStage<Result> monthly() {
        return CompletableFuture.supplyAsync(() -> Results.ok(monthly.render(this.monthlyTemplateBean)));
    }

    public CompletionStage<Result> yearly() {
        return CompletableFuture.supplyAsync(() -> Results.ok(yearly.render(this.yearlyTemplateBean)));
    }

}
