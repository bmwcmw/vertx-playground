package support;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.IOException;
import java.util.Date;

@SpringBootApplication
public class MongoLauncher {

    public static void main(String[] args) throws IOException {
        new SpringApplicationBuilder().sources(MongoLauncher.class).run(args);
        MongodStarter starter = MongodStarter.getDefaultInstance();

        String bindIp = "localhost";
        int port = 12345;
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = null;
        try {
            mongodExecutable = starter.prepare(mongodConfig);
            mongodExecutable.start();

            MongoClient mongo = new MongoClient(bindIp, port);
            MongoDatabase db = mongo.getDatabase("testDb");
            db.createCollection("testCol");
            MongoCollection col = db.getCollection("testCol");
            col.insertOne(new BasicDBObject("testDoc", new Date()));
        } finally {
            if (mongodExecutable != null)
                mongodExecutable.stop();
        }
    }

}
