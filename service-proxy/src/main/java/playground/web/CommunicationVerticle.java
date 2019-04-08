package playground.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceBinder;
import playground.domain.CommunicationService;
import playground.repository.CommunicationRepository;

public class CommunicationVerticle extends AbstractVerticle {

    private Logger log = LoggerFactory.getLogger(CommunicationVerticle.class);

    static final String COMMUNICATION = "CommChannel";

    public static final String SERVICE_ADDRESS = "serv-address";
    public static final String REPOSITORY_ADDRESS = "repo-address";

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<String>consumer(COMMUNICATION)
                .handler(getByCommunicationService());

        // Keep in order
        CommunicationRepository communicationRepository = CommunicationRepository.create();
        MessageConsumer<JsonObject> repositoryRegister = new ServiceBinder(vertx)
                .setAddress(REPOSITORY_ADDRESS)
                .register(CommunicationRepository.class, communicationRepository);

        CommunicationService communicationService = CommunicationService.create(vertx);
        MessageConsumer<JsonObject> serviceRegister = new ServiceBinder(vertx)
                .setAddress(SERVICE_ADDRESS)
                .register(CommunicationService.class, communicationService);

//        serviceRegister.unregister();
//        repositoryRegister.unregister();

        log.info("CommunicationVerticle started");
    }

    private Handler<Message<String>> getByCommunicationService() {
        return msg -> vertx.<String>executeBlocking(future -> {
            CommunicationService serviceProxy = CommunicationService.createProxy(vertx, SERVICE_ADDRESS);
            serviceProxy.getAllThings(result -> {
                if (result.succeeded()) {
                    log.info("CommunicationService replying " + result.result());
                    future.complete(result.result());
                }
            });
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause().toString());
            }
        });
    }
}