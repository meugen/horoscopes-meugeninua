package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Result;
import ua.meugen.horoscopes.controllers.Application;
import ua.meugen.horoscopes.helpers.ObfuscateHelper;
import ua.meugen.horoscopes.template.bean.portal.interpretations.AmuletsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.interpretations.DreamsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.interpretations.NamesTemplateBean;
import views.html.interpretations.amulets;
import views.html.interpretations.dreams;
import views.html.interpretations.names;

import javax.inject.Inject;

public final class Interpretations {

    @Inject
    private NamesTemplateBean namesTemplateBean;

    @Inject
    private DreamsTemplateBean dreamsTemplateBean;

    @Inject
    private AmuletsTemplateBean amuletsTemplateBean;

    @Inject
    private ObfuscateHelper obfuscateHelper;

    public F.Promise<Result> names() {
        return F.Promise.promise(() -> obfuscateHelper.ok(names.render(this.namesTemplateBean)));
    }

    public F.Promise<Result> dreams() {
        return F.Promise.promise(() -> obfuscateHelper.ok(dreams.render(this.dreamsTemplateBean)));
    }

    public F.Promise<Result> amulets() {
        return F.Promise.promise(() -> obfuscateHelper.ok(amulets.render(this.amuletsTemplateBean)));
    }
}
