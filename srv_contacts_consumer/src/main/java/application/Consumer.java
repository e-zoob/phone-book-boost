package application;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import domain.Contact;
import persistence.Persistence;
import java.nio.charset.StandardCharsets;

import static common.JsonConverter.Deserialize;

public class Consumer {

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Channel channel;
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare("phone-book", false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            Contact contact = Deserialize(message, new Gson());
            Persistence persistence = new Persistence();
            persistence.InsertContact("meteor", "contacts", contact);

        };

        channel.basicConsume("phone-book", true, deliverCallback, consumerTag -> {});
    }

}