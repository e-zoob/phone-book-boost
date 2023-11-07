package main

import (

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"

)

type Datastore struct {
    Client *mongo.Client
}

func (ds *Datastore) SaveContact(contact Contact) error {
    coll := db.Client.Database(os.Getenv("MONGO_DBNAME")).Collection(os.Getenv("MONGO_COLLECTION_NAME"))
    res, err := coll.InsertOne(context.TODO(), contact)
    if err != nil {
        log.Println("Failed to insert contact:", err)
        return err
    }

    log.Printf("Contact saved")
    return res
}