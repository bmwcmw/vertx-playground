import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

import java.util.Random;

public class Endpoint implements Handler<RoutingContext> {

    private final EventBus eventBus;
    private final String player1;
    private final String player2;

    @Inject
    public Endpoint(
            EventBus eventBus,
            @Named("player1") String player1,
            @Named("player2") String player2) {

        this.eventBus = eventBus;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void handle(RoutingContext request) {
        Random tournamentKarma = new Random();
        boolean firstPlayerIsAWinner = tournamentKarma.nextBoolean();

        String winner = firstPlayerIsAWinner ? player1 : player2;
        eventBus.send(winner, "Congratulation Wimbledon Winner!");
        request.response().end(winner);
    }
}