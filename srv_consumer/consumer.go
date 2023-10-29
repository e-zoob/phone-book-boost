package main

import (
	"context"
	"encoding/json"
	"log"
	"os"

	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"

	amqp "github.com/rabbitmq/amqp091-go"
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Panicf("%s: %s", msg, err)
	}
}

func main() {

	conn, err := amqp.Dial(os.Getenv("RABBITMQ"))

	failOnError(err, "Failed to connect to RabbitMQ")
	defer conn.Close()

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")
	defer ch.Close()

	q, err := ch.QueueDeclare(
		os.Getenv("QUEUE_NAME"),
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
	opts := options.Client().ApplyURI(os.Getenv("MONGO_CONNSTRING"))

	client, err := mongo.Connect(ctx, opts)
	if err != nil {
		panic(err)
	}
	defer client.Disconnect(ctx)
	consumeMessages(msgs, client)

}

func handleMessage(d amqp.Delivery, client *mongo.Client) error {
	var contact Contact
	if err := json.Unmarshal(d.Body, &contact); err != nil {
		log.Println("Failed to unmarshal:", err)
		return err
	}

	coll := client.Database(os.Getenv("MONGO_DBNAME")).Collection(os.Getenv("MONGO_COLLECTION_NAME"))
	_, err := coll.InsertOne(context.TODO(), contact)
	if err != nil {
		log.Println("Failed to insert contact:", err)
		return err
	}

	log.Printf("Contact saved")
	return nil
}

func consumeMessages(msgs <-chan amqp.Delivery, client *mongo.Client) {
	for d := range msgs {
		if err := handleMessage(d, client); err != nil {
			log.Println("Failed to handle message:", err)
		}
	}
}
