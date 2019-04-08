package playground;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;
import playground.web.CommunicationVerticle;
import playground.web.ServerVerticle;

public class Application {
    public static void main(String[] args) {
        System.setProperty(LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());
        Logger log = LoggerFactory.getLogger(Application.class);

        // Setup the http server
        log.info("Starting server");
        Vertx vertx = Vertx.vertx();
        vertx.exceptionHandler(throwable -> {
            throwable.printStackTrace();
            log.error(throwable);
        });
        vertx.deployVerticle(ServerVerticle.class.getName(), res -> {
            if (res.succeeded()) {
                log.info("ServerVerticle deployment id is: " + res.result());
            } else {
                log.error("ServerVerticle deployment failed");
                res.cause().printStackTrace();
                log.error(res.cause());
            }
        });
        vertx.deployVerticle(CommunicationVerticle.class.getName(), res -> {
            if (res.succeeded()) {
                log.info("CommunicationVerticle deployment id is: " + res.result());
            } else {
                log.error("CommunicationVerticle deployment failed");
                res.cause().printStackTrace();
                log.error(res.cause());
            }
        });
    }
}
