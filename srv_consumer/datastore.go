package main

import (
	"context"
	"log"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type Datastore struct {
	Client *mongo.Client
}

func NewDatastore(mongoURI string) (*Datastore, error) {
	clientOptions := options.Client().ApplyURI(mongoURI)

	client, err := mongo.Connect(context.TODO(), clientOptions)
	if err != nil {
		return nil, err
	}

	return &Datastore{Client: client}, nil
}

func (ds *Datastore) SaveContact(contact Contact) error {
	coll := ds.Client.Database("phonebook").Collection("contacts")
	_, err := coll.InsertOne(context.Background(), contact)
	if err != nil {
		return err
	}
	log.Printf("Contact saved")
	return nil
}
