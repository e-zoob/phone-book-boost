from fastapi import FastAPI
from .contact import Contact
from .broker import publish

app = FastAPI()

@app.get("/")
async def root():
    return{"message": "I'm a phone book"}

@app.post("/contacts/")
async def create_address(contact: Contact):
    publish(contact)
    return 


    


