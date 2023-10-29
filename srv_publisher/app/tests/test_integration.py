import pytest
import pika
import httpx
import time
from app import broker
from fastapi.testclient import TestClient
from ..main import app

client = TestClient(app)

def test_post_and_check_rabbitmq_queue():

    response = client.post("/contacts/", json={"name":"Test Value", "number":"12345"})
    assert response.status_code == 200

    queue_name = "phone-book"
    connection, channel = broker.connect()
    queue = channel.queue_declare(queue=queue_name, durable=True, passive=True)

    message_count = queue.method.message_count
    assert message_count == 1

    method_frame, header_frame, body = channel.basic_get(queue=queue_name)
    assert body.decode() == '{\n    "name": "Test Value",\n    "number": 12345\n}'

    channel.basic_ack(method_frame.delivery_tag)  