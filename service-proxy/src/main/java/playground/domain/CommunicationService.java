package playground.domain;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@ProxyGen
public interface CommunicationService {

    static CommunicationService create(Vertx vertx) {
        return new CommunicationServiceImpl(vertx);
    }

    static CommunicationService createProxy(Vertx vertx, String address) {
        return new ServiceProxyBuilder(vertx).setAddress(address).build(CommunicationService.class);
    }

    @Fluent
    CommunicationService getAllThingsFluent();

    /**
     *
     * @return
     */
    void getAllThings(Handler<AsyncResult<String>> resultHandler);

}