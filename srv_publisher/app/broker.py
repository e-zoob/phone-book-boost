from .contact import Contact
import pika
import json

def connect():
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()
    channel.queue_declare(queue='phone-book')
    return connection, channel

def close(connection: pika.BlockingConnection):
    connection.close()
    return

def publish(contact: Contact): 
    connection, channel = connect()
    body_contact = json.dumps(contact, indent=4, default=str)
    
    channel.basic_publish(exchange='',
                          routing_key='phone-book',
                          body=body_contact)
    close(connection)
    return 
