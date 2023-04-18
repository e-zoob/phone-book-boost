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



        MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
        MongoDatabase database = mongoClient.getDatabase("meteor");
        String collectionName = "contacts";
        boolean collectionExist = database.listCollectionNames().into(new ArrayList()).contains(collectionName);

        if (!collectionExist){
            database.createCollection("contacts");
        }
        MongoCollection<Document> contacts = database.getCollection(collectionName);



        database.listCollectionNames().forEach(System.out::println);


    }

}