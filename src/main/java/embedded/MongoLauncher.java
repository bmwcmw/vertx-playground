package embedded;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.Document;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
public class MongoLauncher {

    public static void main(String[] args) throws IOException {
        System.setProperty("http.proxyHost", "proxyweb");
        System.setProperty("http.proxyPort", "8080");

        new SpringApplicationBuilder().sources(MongoLauncher.class).run(args);
        MongodStarter starter = MongodStarter.getDefaultInstance();

        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = null;
        MongodProcess mongod = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            mongod = mongodExecutable.start();

            MongoClient mongo = new MongoClient(bindIp, port);
            MongoDatabase db = mongo.getDatabase("testDb");
            db.createCollection("testCol");
            MongoCollection col = db.getCollection("testCol");
            col.insertOne(new Document("testDoc", new Date()));

            // standby here to prevent exit
        } finally {
            if (mongod != null) {
                mongod.stop();
            }
            if (mongodExecutable != null)
                mongodExecutable.stop();
        }
    }

}
