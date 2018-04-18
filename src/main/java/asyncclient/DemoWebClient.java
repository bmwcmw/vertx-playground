package asyncclient;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

import javax.ws.rs.core.MediaType;

public class DemoWebClient {

    private final String id;
    private final WebClient client;

    public DemoWebClient(String id, Vertx vertx) {
        this.id = id;
        this.client = WebClient.create(vertx);
    }

    public Future<JsonObject> fetchResult() {
        // Or CompletableFuture
        Future<JsonObject> future = Future.future();
        this.client.get(443, "jsonplaceholder.typicode.com", "/todos")
                .ssl(true)
                .putHeader(HttpHeaders.CONTENT_TYPE.toString(), MediaType.APPLICATION_JSON)
                .as(BodyCodec.jsonArray())
                .send(ar -> {
                    if (ar.succeeded()) {
                        HttpResponse<JsonArray> response = ar.result();
                        JsonArray jsonArr = response.body();
                        System.out.println("Got HTTP response body by " + this.id + " with length " + jsonArr.size());
                        System.out.println();
                    } else {
                        System.out.println("Failed by " + this.id);
                        ar.cause().printStackTrace();
                    }
                });
        return future;
    }
}
