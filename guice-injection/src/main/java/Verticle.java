import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class Verticle extends AbstractVerticle {

    @Inject
    private Endpoint tournamentWinnerEndpoint;

    @Inject
    @Named("port")
    private String port;

    @Override
    public void start() throws Exception {
        //Binds all dependencies to already initialized vertx instance
        Guice.createInjector(new Module(vertx)).injectMembers(this);

        Router router = Router.router(vertx);
        router.get("/wimbledon/winner")
                .handler(tournamentWinnerEndpoint);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(Integer.valueOf(port));

    }
}