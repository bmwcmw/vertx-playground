package playground.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class ServerVerticle extends AbstractVerticle {

    private Logger log = LoggerFactory.getLogger(ServerVerticle.class);

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.get("/api/creative")
                .handler(this::getAllArticlesHandler);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8999));

        log.info("ServerVerticle started");
    }

    private void getAllArticlesHandler(RoutingContext routingContext) {
        vertx.eventBus().<String>send(CommunicationVerticle.COMMUNICATION, "",
                result -> {
                    if (result.succeeded()) {
                        String chunk = result.result() == null ? result.result().body() : null;
                        log.info("ServerVerticle replying " + chunk);
                        routingContext.response()
                                .putHeader("content-type", "application/json")
                                .setStatusCode(200)
                                .end(chunk);
                    } else {
                        result.cause().printStackTrace();
                        log.error(result.cause());
                        routingContext.response()
                                .setStatusCode(500)
                                .end();
                    }
                });
    }

}
