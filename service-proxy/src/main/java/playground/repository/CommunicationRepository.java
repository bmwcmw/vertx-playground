package playground.repository;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@ProxyGen
public interface CommunicationRepository {

    static CommunicationRepository create() {
        return new CommunicationRepositoryImpl();
    }

    static CommunicationRepository createProxy(Vertx vertx, String address) {
        return new ServiceProxyBuilder(vertx).setAddress(address).build(CommunicationRepository.class);
    }

    @Fluent
    CommunicationRepository getResultFluent();

    public void getResult(Handler<AsyncResult<JsonObject>> resultHandler);
}
