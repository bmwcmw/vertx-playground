package asyncclient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

import java.util.LinkedList;
import java.util.stream.IntStream;

public class AsyncClientVerticle extends AbstractVerticle {

    private static Vertx vertx;

    public static void main(String[] args) {

        // deploy it
        vertx = Vertx.vertx();
        // The number of instances deployed is the number of cores of the machine.
        int instances = Runtime.getRuntime().availableProcessors();

        DeploymentOptions opt = new DeploymentOptions().setInstances(instances);
        vertx.deployVerticle(AsyncClientVerticle.class, opt);
    }


    @Override
    public void start() {
        LinkedList<DemoWebClient> providers = new LinkedList<>();
        IntStream.range(0, 3).forEach(idx ->
                providers.add(idx, new DemoWebClient(String.valueOf(idx), vertx))
        );
        providers.getFirst().fetchResult(); // find a way to iterate providers, pass to next one by one if first ones failed.
    }
}
