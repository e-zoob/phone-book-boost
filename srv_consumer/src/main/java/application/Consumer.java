package application;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.bson.Document;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
        };

        channel.basicConsume("phone-book", true, deliverCallback, consumerTag -> {});







        //database.listCollectionNames().forEach(System.out::println);


    }

}