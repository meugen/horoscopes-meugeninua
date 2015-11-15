package ua.meugen.horoscopes.controllers.portal;

import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.controllers.Application;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.ChinasTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.DruidsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.FlowersTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.JapansTemplateBean;
import views.html.other.horoscopes.flowers;
import views.html.other.horoscopes.druids;
import views.html.other.horoscopes.japans;
import views.html.other.horoscopes.chinas;

import javax.inject.Inject;

public final class OtherHoroscopes {

    @Inject
    private FlowersTemplateBean flowersTemplateBean;

    @Inject
    private DruidsTemplateBean druidsTemplateBean;

    @Inject
    private JapansTemplateBean japansTemplateBean;

    @Inject
    private ChinasTemplateBean chinasTemplateBean;

    public F.Promise<Result> flowers() {
        return F.Promise.promise(() -> Application.ok(flowers.render(this.flowersTemplateBean)));
    }

    public F.Promise<Result> druids() {
        return F.Promise.promise(() -> Application.ok(druids.render(this.druidsTemplateBean)));
    }

    public F.Promise<Result> japans() {
        return F.Promise.promise(() -> Application.ok(japans.render(this.japansTemplateBean)));
    }

    public F.Promise<Result> chinas() {
        return F.Promise.promise(() -> Application.ok(chinas.render(this.chinasTemplateBean)));
    }
}
