package playground.repository;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class CommunicationRepositoryImpl implements CommunicationRepository {

    @Override
    public CommunicationRepository getResultFluent() {
        return this;
    }

    @Override
    public void getResult(Handler<AsyncResult<JsonObject>> resultHandler) {
        resultHandler.handle(Future.succeededFuture(new JsonObject().put("status", "OK")));
    }
}
