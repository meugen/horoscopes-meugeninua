package ua.meugen.horoscopes.controllers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.translate.*;

/**
 * Created by meugen on 14.01.15.
 */
@Service
public final class Translate extends Controller {

    @Autowired
    private AmuletsTranslateAction amuletsTranslateAction;

    @Autowired
    private ChinasTranslateAction chinasTranslateAction;

    @Autowired
    private DreamsTranslateAction dreamsTranslateAction;

    @Autowired
    private DruidsTranslateAction druidsTranslateAction;

    @Autowired
    private FlowersTranslateAction flowersTranslateAction;

    @Autowired
    private JapansTranslateAction japansTranslateAction;

    public F.Promise<Result> flowersTo(final String lang) {
        this.flowersTranslateAction.setLang(lang);
        return this.flowersTranslateAction.execute();
    }

    public F.Promise<Result> druidsTo(final String lang) {
        this.druidsTranslateAction.setLang(lang);
        return this.druidsTranslateAction.execute();
    }

    public F.Promise<Result> japansTo(final String lang) {
        this.japansTranslateAction.setLang(lang);
        return this.japansTranslateAction.execute();
    }

    public F.Promise<Result> chinasTo(final String lang) {
        this.chinasTranslateAction.setLang(lang);
        return this.chinasTranslateAction.execute();
    }

    public F.Promise<Result> dreamsTo(final String lang, final Integer max) {
        this.dreamsTranslateAction.setLangAndLimit(lang, max);
        return this.dreamsTranslateAction.execute();
    }

    public F.Promise<Result> amuletsTo(final String lang, final Integer max) {
        this.amuletsTranslateAction.setLangAndLimit(lang, max);
        return this.amuletsTranslateAction.execute();
    }
}
