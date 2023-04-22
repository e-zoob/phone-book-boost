package persistence;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import domain.Contact;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
public class PersistenceHandler {

    private MongoDatabase GetMongoDb(String dbName) {

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        MongoDatabase database;
        try (MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017")) {
            database = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
        }
        return database;

    }
    //TODO pass dbName in configuration.
    public MongoCollection<Contact> GetCollection(String dbName, String collectionName){
        MongoDatabase database = GetMongoDb(dbName);
        boolean collectionExist = database.listCollectionNames().into(new ArrayList<String>()).contains(collectionName);

        if (!collectionExist){
            database.createCollection(collectionName);
        }
        return database.getCollection(collectionName, Contact.class);
    }

    public void InsertContact(String dbName, String collectionName, Contact contact){
        MongoCollection<Contact> collection = GetCollection(dbName, collectionName);
        collection.insertOne(contact);
    }

    public List<Contact> GetAllContacts(String dbName, String collectionName){
        List<Contact> contacts = new ArrayList<>();
        MongoCollection<Contact> collection = GetCollection(dbName, collectionName);
        collection.find().into(contacts);
        return contacts;
    }

}
