package ua.meugen.horoscopes.controllers.content;

import com.google.inject.Inject;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.translate.*;
import ua.meugen.horoscopes.actions.requests.BaseTranslateRequest;
import ua.meugen.horoscopes.actions.requests.LimitTranslateRequest;

/**
 * Created by meugen on 14.01.15.
 */
public final class Translate extends Controller {

    @Inject
    private AmuletsTranslateAction amuletsTranslateAction;

    @Inject
    private ChinasTranslateAction chinasTranslateAction;

    @Inject
    private DreamsTranslateAction dreamsTranslateAction;

    @Inject
    private DruidsTranslateAction druidsTranslateAction;

    @Inject
    private FlowersTranslateAction flowersTranslateAction;

    @Inject
    private JapansTranslateAction japansTranslateAction;

    public F.Promise<Result> flowersTo(final String lang) {
        final BaseTranslateRequest request = new BaseTranslateRequest();
        request.setLang(lang);
        return this.flowersTranslateAction.execute(request);
    }

    public F.Promise<Result> druidsTo(final String lang) {
        final BaseTranslateRequest request = new BaseTranslateRequest();
        request.setLang(lang);
        return this.druidsTranslateAction.execute(request);
    }

    public F.Promise<Result> japansTo(final String lang) {
        final BaseTranslateRequest request = new BaseTranslateRequest();
        request.setLang(lang);
        return this.japansTranslateAction.execute(request);
    }

    public F.Promise<Result> chinasTo(final String lang) {
        final BaseTranslateRequest request = new BaseTranslateRequest();
        request.setLang(lang);
        return this.chinasTranslateAction.execute(request);
    }

    public F.Promise<Result> dreamsTo(final String lang, final Integer max) {
        final LimitTranslateRequest request = new LimitTranslateRequest();
        request.setLang(lang);
        request.setLimit(max);
        return this.dreamsTranslateAction.execute(request);
    }

    public F.Promise<Result> amuletsTo(final String lang, final Integer max) {
        final LimitTranslateRequest request = new LimitTranslateRequest();
        request.setLang(lang);
        request.setLimit(max);
        return this.amuletsTranslateAction.execute(request);
    }
}
