from fastapi.testclient import TestClient
from .main import app
from unittest.mock import patch

client = TestClient(app)

def test_root():

    response = client.get("/")

    assert response.status_code == 200
    assert response.json() == {"message": "I'm a phone book"}


@patch('pika.BlockingConnection')
def test_create_contact(mocker):

    response = client.post("/contacts/", json={"name":"Test Value", "number":"12345"})

    assert response.status_code == 200
    assert response.json() == {"name":"Test Value", "number":12345}
    

@patch('pika.BlockingConnection')
def test_create_bad_Contact(mocker):
    
    response = client.post("/contacts/", json={"name": "Test value", "number":"bad number"})

    assert response.status_code == 422
    assert response.json() == {
                               "detail": [
                                 {
                                    "loc": [
                                        "body",
                                        "number"
                                    ],
                                    "msg": "value is not a valid integer",
                                    "type": "type_error.integer"
                                  }
                                ]      
                               }
    

@patch('pika.BlockingConnection', return_value=Exception)
def test_create_contact_bad_connection(mocker):

    response = client.post("/contacts/", json={"name":"Test Value", "number":"12345"})

    assert response.status_code == 503
    assert response.json() == {"detail":"Service unavailable"}
    