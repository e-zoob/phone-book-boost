from fastapi import FastAPI
from pydantic import BaseModel

class Contact(BaseModel):
    name: str
    number: str

app = FastAPI()

@app.get("/")
async def root():
    return{"message": "I'm a phone book"}

@app.post("/contacts/")
async def create_address(contact: Contact):
    return contact


    


