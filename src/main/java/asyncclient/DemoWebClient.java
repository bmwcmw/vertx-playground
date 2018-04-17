package asyncclient;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class DemoWebClient {

    private final String id;
    private final WebClient client;

    public DemoWebClient(String id, Vertx vertx) {
        this.id = id;
        this.client = WebClient.create(vertx);
    }

    public Future<JsonObject> fetchResult() {
        Future<JsonObject> future = Future.future();
        this.client.get(80, "https://jsonplaceholder.typicode.com", "/todos")
                .as(BodyCodec.jsonObject())
                .send(ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<JsonObject> response = ar.result();
                        System.out.println("Got HTTP response body by " + this.id);
                        System.out.println(response.body().encodePrettily());
                    } else {
                        ar.cause().printStackTrace();
                    }
                });
        return future;
    }
}
