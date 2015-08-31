package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.template.bean.portal.interpretations.AmuletsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.interpretations.DreamsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.interpretations.NamesTemplateBean;
import views.html.interpretations.names;
import views.html.interpretations.dreams;
import views.html.interpretations.amulets;

import javax.inject.Inject;

public final class Interpretations extends Controller {

    @Inject
    private NamesTemplateBean namesTemplateBean;

    @Inject
    private DreamsTemplateBean dreamsTemplateBean;

    @Inject
    private AmuletsTemplateBean amuletsTemplateBean;

    public F.Promise<Result> names() {
        return F.Promise.promise(() -> ok(names.render(this.namesTemplateBean)));
    }

    public F.Promise<Result> dreams() {
        return F.Promise.promise(() -> ok(dreams.render(this.dreamsTemplateBean)));
    }

    public F.Promise<Result> amulets() {
        return F.Promise.promise(() -> ok(amulets.render(this.amuletsTemplateBean)));
    }
}
