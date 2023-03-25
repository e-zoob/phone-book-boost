from pydantic import BaseModel

class Contact(BaseModel):
    name: str
    number: str
