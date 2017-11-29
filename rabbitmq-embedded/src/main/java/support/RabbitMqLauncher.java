package support;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMq;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMqConfig;
import io.arivera.oss.embedded.rabbitmq.PredefinedVersion;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqLauncher {

    public static void main(String[] args) {
        EmbeddedRabbitMq rabbitMq = null;
        Connection connection = null;
        Channel channel = null;
        try {
            EmbeddedRabbitMqConfig config = new EmbeddedRabbitMqConfig.Builder().version(PredefinedVersion.LATEST).build();
            rabbitMq = new EmbeddedRabbitMq(config);
            rabbitMq.start();
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setVirtualHost("/");
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");

            connection = connectionFactory.newConnection();
            System.out.println("Connection is Open : " + connection.isOpen());
            channel = connection.createChannel();
            System.out.println("Channel is Open : " + channel.isOpen());

            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rabbitMq.stop();
        }
    }

}
