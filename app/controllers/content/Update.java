package controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.controllers.content.CheckJsonHelper;
import helpers.controllers.content.update.UpdateHelpersFactory;
import helpers.controllers.content.update.UpdateMonthlyHelper;
import helpers.controllers.content.update.UpdateYearlyHelper;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by meugen on 03.07.14.
 */
public final class Update extends Controller {

    private static final String URL_KEY = "url";
    private static final String MONTH_KEY = "month";
    private static final String YEAR_KEY = "year";

    public static F.Promise<Result> all() {
        return UpdateHelpersFactory.newUpdateAllHelper(request().uri()).execute();
    }

    public static F.Promise<Result> daily() {
        return UpdateHelpersFactory.newUpdateDailyHelper(request().uri()).execute();
    }

    public static F.Promise<Result> weekly() {
        return UpdateHelpersFactory.newUpdateWeeklyHelper(request().uri()).execute();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> monthly() {
        return monthly(false);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> rewriteMonthly() {
        return monthly(true);
    }

    private static F.Promise<Result> monthly(final boolean rewrite) {
        return new CheckJsonHelper(request().body().asJson(),
                new String[] { URL_KEY, MONTH_KEY, YEAR_KEY }) {
            protected Result onJsonValid(final JsonNode json) {
                return monthly(rewrite, json);
            }
        }.promiseCheck();
    }

    private static Result monthly(final boolean rewrite, final JsonNode json) {
        final UpdateMonthlyHelper helper = new UpdateMonthlyHelper(request().uri());
        helper.setMonth(json.get(MONTH_KEY).asText());
        helper.setYear(json.get(YEAR_KEY).asText());
        helper.setRewrite(rewrite);
        helper.setUrl(json.get(URL_KEY).asText());
        return helper.execute().get(Long.MAX_VALUE);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> yearly() {
        return yearly(false);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> rewriteYearly() {
        return yearly(true);
    }

    private static F.Promise<Result> yearly(final boolean rewrite) {
        return new CheckJsonHelper(request().body().asJson(),
                new String[] { URL_KEY, YEAR_KEY }) {
            protected Result onJsonValid(final JsonNode json) {
                return yearly(json, rewrite);
            }
        }.promiseCheck();
    }

    private static Result yearly(final JsonNode json, final boolean rewrite) {
        final UpdateYearlyHelper helper = new UpdateYearlyHelper(request().uri());
        helper.setRewrite(rewrite);
        helper.setUrl(json.get(URL_KEY).asText());
        helper.setYear(json.get(YEAR_KEY).asText());
        return helper.execute().get(Long.MAX_VALUE);
    }
}
