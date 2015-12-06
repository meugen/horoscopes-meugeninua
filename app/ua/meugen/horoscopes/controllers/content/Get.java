package ua.meugen.horoscopes.controllers.content;

import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.actions.content.get.*;
import ua.meugen.horoscopes.entities.*;

import javax.inject.Inject;

public final class Get {

    @Inject
    private GetHoroscopeAction getHoroscopeAction;

    @Inject
    private GetHoroscopesForAction getHoroscopesForAction;

    @Inject
    private GetKeysAction getKeysAction;

    @Inject
    private GetKeysForAction getKeysForAction;

    @Inject
    private AmuletGetByIdAction getAmuletAction;

    @Inject
    private SimpleGetByIdAction<Dream> getDreamAction;

    @Inject
    private SimpleGetByIdAction<Name> getNameAction;

    @Inject
    private SimpleGetByIdAction<Druid> getDruidAction;

    @Inject
    private SimpleGetByIdAction<China> getChinaAction;

    @Inject
    private SimpleGetByIdAction<Flower> getFlowerAction;

    @Inject
    private SimpleGetByIdAction<Japan> getJapanAction;

    public F.Promise<Result> amuletById(final Integer id) {
        return this.getAmuletAction.execute(id);
    }

    public F.Promise<Result> chinaById(final Integer id) {
        return this.getChinaAction.execute(id);
    }

    public F.Promise<Result> dreamById(final Integer id) {
        return this.getDreamAction.execute(id);
    }

    public F.Promise<Result> druidById(final Integer id) {
        return this.getDruidAction.execute(id);
    }

    public F.Promise<Result> flowerById(final Integer id) {
        return this.getFlowerAction.execute(id);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> horoscope() {
        return this.getHoroscopeAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> horoscopesFor() {
        return this.getHoroscopesForAction.execute(Controller.request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> keys() {
        return this.getKeysAction.execute(Controller.request().body().asJson());
    }

    public F.Promise<Result> keysFor() {
        return this.getKeysForAction.execute();
    }

    public F.Promise<Result> japanById(final Integer id) {
        return this.getJapanAction.execute(id);
    }

    public F.Promise<Result> nameById(final Integer id) {
        return this.getNameAction.execute(id);
    }
}
