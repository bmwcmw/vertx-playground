package asyncclient;

import io.vertx.core.*;

import java.util.*;
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
        // find a way to iterate providers, pass to next one by one if first ones failed.
    }

    private void fetchConcurrently() {
        List<Future> providerFutures = new ArrayList<>();
        IntStream.range(0, 3).forEach(
            idx -> {
                DemoDataProvider provider = new DemoDataProvider(idx, vertx);
                providerFutures.add(provider.fetchResultFuture());
            }
        );
        CompositeFuture.any(providerFutures).setHandler(ar -> {
            if (ar.succeeded()) {
                // At least one is succeeded
                System.out.println(findFirstFromCompositeFuture(ar.result().list()).get());
            } else {
                // All failed
                System.out.println("All failed");
            }
        });
    }

    private Optional<Object> findFirstFromCompositeFuture(List<Object> list) {
        return list.stream().filter(Objects::nonNull).findFirst();
    }
}
