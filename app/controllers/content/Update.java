package controllers.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.DatabaseHelper;
import helpers.controllers.Response;
import helpers.controllers.content.update.UpdateHelpersFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.libs.F;
import play.libs.Json;
import play.libs.XML;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by meugen on 03.07.14.
 */
public final class Update extends Controller {

    public static F.Promise<Result> all() {
        return UpdateHelpersFactory.newUpdateAllHelper(request().uri()).execute();
    }

    public static F.Promise<Result> daily() {
        return UpdateHelpersFactory.newUpdateDailyHelper(request().uri()).execute();
    }

    public static F.Promise<Result> weekly() {
        return UpdateHelpersFactory.newUpdateWeeklyHelper(request().uri()).execute();
    }
}
