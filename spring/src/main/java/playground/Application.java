package playground;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import playground.web.CommunicationVerticle;
import playground.web.ServerVerticle;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    @Autowired
    private ServerVerticle serverVerticle;

    @Autowired
    private CommunicationVerticle communicationVerticle;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(serverVerticle);
        vertx.deployVerticle(communicationVerticle);
    }
}
