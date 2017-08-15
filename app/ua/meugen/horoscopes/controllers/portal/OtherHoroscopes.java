package ua.meugen.horoscopes.controllers.portal;

import play.mvc.Result;
import play.mvc.Results;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.ChinasTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.DruidsTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.FlowersTemplateBean;
import ua.meugen.horoscopes.template.bean.portal.other.horoscopes.JapansTemplateBean;
import views.html.other.horoscopes.chinas;
import views.html.other.horoscopes.druids;
import views.html.other.horoscopes.flowers;
import views.html.other.horoscopes.japans;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Singleton
public final class OtherHoroscopes {

    @Inject
    private FlowersTemplateBean flowersTemplateBean;

    @Inject
    private DruidsTemplateBean druidsTemplateBean;

    @Inject
    private JapansTemplateBean japansTemplateBean;

    @Inject
    private ChinasTemplateBean chinasTemplateBean;

    public CompletionStage<Result> flowers() {
        return CompletableFuture.supplyAsync(() -> Results.ok(flowers.render(this.flowersTemplateBean)));
    }

    public CompletionStage<Result> druids() {
        return CompletableFuture.supplyAsync(() -> Results.ok(druids.render(this.druidsTemplateBean)));
    }

    public CompletionStage<Result> japans() {
        return CompletableFuture.supplyAsync(() -> Results.ok(japans.render(this.japansTemplateBean)));
    }

    public CompletionStage<Result> chinas() {
        return CompletableFuture.supplyAsync(() -> Results.ok(chinas.render(this.chinasTemplateBean)));
    }
}
