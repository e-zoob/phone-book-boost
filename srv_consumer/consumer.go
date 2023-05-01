package main

import (
	"context"
	"encoding/json"
	"log"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"

	amqp "github.com/rabbitmq/amqp091-go"
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Panicf("%s: %s", msg, err)
	}
}

type Contact struct {
	Name   string `json:"name" bson:"name,omitempty"`
	Number int    `json:"number" bson:"number,omitempty"`
}

func main() {

	conn, err := amqp.Dial("amqp://guest:guest@localhost:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	q, err := ch.QueueDeclare(
		"phone-book",
		false,
		false,
		false,
		false,
		nil,
	)
	failOnError(err, "Failed to declare a queue")

	msgs, err := ch.Consume(
		q.Name,
		"",
		true,
		false,
		false,
		false,
		nil,
	)

	failOnError(err, "Failed to register a consumer")
	ctx := context.TODO()
	opts := options.Client().ApplyURI("mongodb://localhost:27017")

	client, err := mongo.Connect(ctx, opts)
	if err != nil {
		panic(err)
	}
	defer client.Disconnect(ctx)

	var forever chan struct{}

	go func() {
		for d := range msgs {
			var contact Contact
			if err := json.Unmarshal(d.Body, &contact); err != nil {
				log.Println("Failed to unmarshal:", err)
			} else {
				coll := client.Database("phone-contacts").Collection("contacts")
				_, err = coll.InsertOne(context.TODO(), contact)
				if err != nil {
					panic(err)
				}
				log.Printf("Contact saved")
			}
		}
	}()

	log.Printf(" [*] Waiting for messages. To exit press CTRL+C")
	<-forever
}
