package asyncclient;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

import javax.ws.rs.core.MediaType;
import java.util.concurrent.CompletableFuture;

public class DemoDataProvider {

    private final int id;
    private final WebClient client;

    public DemoDataProvider(int id, Vertx vertx) {
        this.id = id;
        this.client = WebClient.create(vertx);
    }

    public Future<JsonArray> fetchResultFuture() {
        Future<JsonArray> jsonObjectFuture = Future.future();
        this.client.get(443, "jsonplaceholder.typicode.com", "/todos")
            .ssl(true)
            .putHeader(HttpHeaders.CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON)
            .as(BodyCodec.jsonArray())
            .send(ar -> {
                if (ar.succeeded() && (this.id == 2)) { // only 2 will success
                    HttpResponse<JsonArray> response = ar.result();
                    JsonArray jsonArr = response.body();
                    System.out.println("Got HTTP response body by " + this.id + " with length " + jsonArr.size());
                    jsonObjectFuture.complete(jsonArr);
                } else {
                    String msg = "Failed at " + this.id;
                    System.out.println(msg);
                    jsonObjectFuture.fail(new RuntimeException(msg));
                }
            });
        return jsonObjectFuture;
    }
}
