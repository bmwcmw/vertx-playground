package playground.domain;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import playground.repository.CommunicationRepository;

import static playground.web.CommunicationVerticle.REPOSITORY_ADDRESS;

public class CommunicationServiceImpl implements CommunicationService {
    private final CommunicationRepository repositoryProxy;

    public CommunicationServiceImpl(Vertx vertx) {
        this.repositoryProxy = CommunicationRepository.createProxy(vertx, REPOSITORY_ADDRESS);
    }

    @Override
    public CommunicationService getAllThingsFluent() {
        return this;
    }

    public void getAllThings(Handler<AsyncResult<String>> resultHandler) {
        Future<String> repositoryFuture = Future.future();
        repositoryProxy.getResult(result -> {
            if (result.succeeded()) {
                repositoryFuture.complete();
                resultHandler.handle(repositoryFuture);
            } else {
                repositoryFuture.fail(new Exception("Repository failed"));
                resultHandler.handle(repositoryFuture);
            }
        });
    }
}
