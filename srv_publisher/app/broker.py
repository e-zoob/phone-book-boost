from .contact import Contact
import pika
import json
from fastapi.encoders import jsonable_encoder

def connect():
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host='rabbitmq',port=5672)) 
    channel = connection.channel()
    channel.queue_declare(queue='phone-book')
    return connection, channel

def close(connection: pika.BlockingConnection):
    connection.close()
    return

def publish(contact: Contact):

    connection, channel = connect()
    body_contact = jsonable_encoder(contact)
    body_json = json.dumps(body_contact, indent=4, default=str)
    

    channel.basic_publish(exchange='',
                        routing_key='phone-book',
                        body=body_json)
    close(connection)

    return 
