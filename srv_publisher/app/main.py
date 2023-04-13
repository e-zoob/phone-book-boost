from fastapi import FastAPI, HTTPException
from .contact import Contact
from .broker import publish

app = FastAPI()

@app.get("/")
async def root():
    return{"message": "I'm a phone book"}

@app.post("/contacts/")
async def create_address(contact: Contact):
    try:
        publish(contact)
    except Exception as e:
        raise HTTPException(status_code=503, detail="Service unavailable")

    return contact


    


