package ua.meugen.horoscopes.controllers.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.libs.F;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import ua.meugen.horoscopes.actions.controllers.content.get.*;

/**
 * Created by meugen on 02.07.14.
 */
@Service
public final class Get extends Controller {

    @Autowired
    private GetHoroscopeAction getHoroscopeAction;

    @Autowired
    private GetHoroscopesForAction getHoroscopesForAction;

    @Autowired
    private GetKeysAction getKeysAction;

    @Autowired
    private GetKeysForAction getKeysForAction;

    @Autowired
    private SimpleGetByIdAction getAmuletAction;

    @Autowired
    private SimpleGetByIdAction getDreamAction;

    @Autowired
    private SimpleGetByIdAction getNameAction;

    @Autowired
    private SimpleGetByIdAction getDruidAction;

    @Autowired
    private SimpleGetByIdAction getChinaAction;

    @Autowired
    private SimpleGetByIdAction getFlowerAction;

    @Autowired
    private SimpleGetByIdAction getJapanAction;

    public F.Promise<Result> amuletById(final Integer id) {
        return this.getAmuletAction.execute(id);
    }

    public F.Promise<Result> chinaById(final Integer id) {
        return this.getChinaAction.execute(id);
    }

    public F.Promise<Result> dreamById(final Integer id) {
        return this.getChinaAction.execute(id);
    }

    public F.Promise<Result> druidById(final Integer id) {
        return this.getDruidAction.execute(id);
    }

    public F.Promise<Result> flowerById(final Integer id) {
        return this.getFlowerAction.execute(id);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> horoscope() {
        return this.getHoroscopeAction.execute(request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> horoscopesFor() {
        return this.getHoroscopesForAction.execute(request().body().asJson());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public F.Promise<Result> keys() {
        return this.getKeysAction.execute(request().body().asJson());
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
