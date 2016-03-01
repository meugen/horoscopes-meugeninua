package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.template.bean.portal.numerology.*;
import views.html.numerology.*;

import javax.inject.Inject;

public final class Numerology {

    @Inject
    private ChisloDomaTemplateBean chisloDomaTemplateBean;

    @Inject
    private ChisloDruzhbyTemplateBean chisloDruzhbyTemplateBean;

    @Inject
    private ChisloImeniTemplateBean chisloImeniTemplateBean;

    @Inject
    private ChisloSerdtsaTemplateBean chisloSerdtsaTemplateBean;

    @Inject
    private ChisloSovmestimostiTemplateBean chisloSovmestimostiTemplateBean;

    @Inject
    private ChisloUdachiTemplateBean chisloUdachiTemplateBean;

    public F.Promise<Result> chisloDoma() {
        return F.Promise.promise(() -> Results.ok(chislodoma.render(this.chisloDomaTemplateBean)));
    }

    public F.Promise<Result> chisloDruzhby() {
        return F.Promise.promise(() -> Results.ok(chislodruzhby.render(this.chisloDruzhbyTemplateBean)));
    }

    public F.Promise<Result> chisloImeni() {
        return F.Promise.promise(() -> Results.ok(chisloimeni.render(this.chisloImeniTemplateBean)));
    }

    public F.Promise<Result> chisloSerdtsa() {
        return F.Promise.promise(() -> Results.ok(chisloserdtsa.render(this.chisloSerdtsaTemplateBean)));
    }

    public F.Promise<Result> chisloSovmestimosti() {
        return F.Promise.promise(() -> Results.ok(chislosovmestimosti.render(this.chisloSovmestimostiTemplateBean)));
    }

    public F.Promise<Result> chisloUdachi() {
        return F.Promise.promise(() -> Results.ok(chisloudachi.render(this.chisloUdachiTemplateBean)));
    }
}
