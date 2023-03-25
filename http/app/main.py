from fastapi import FastAPI
from pydantic import BaseModel
import pika
import pickle

class Contact(BaseModel):
    name: str
    number: str

# def connect():
#     connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
#     channel = connection.channel()
#     return channel.queue_declare(queue='phone-book')

# def disconnect(connection: pika.BlockingConnection):
#     connection.close()

def publish(contact: Contact): 
    connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
    channel = connection.channel()
    channel.queue_declare(queue='phone-book')
    body_contact = pickle.dumps(contact)
    
    channel.basic_publish(exchange='',
                          routing_key='phone-book',
                          body=body_contact)
    connection.close()


app = FastAPI()

@app.get("/")
async def root():
    return{"message": "I'm a phone book"}

@app.post("/contacts/")
async def create_address(contact: Contact):
    publish(contact)
    return 


    


