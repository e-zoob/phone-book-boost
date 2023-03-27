FROM python:3.10

WORKDIR /phone-book-boot/srv_publisher

COPY ./requirements.txt /phone-book-boot/srv_publisher/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /phone-book-boot/srv_publisher/requirements.txt

COPY ./app /phone-book-boot/srv_publisher/app

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "80"]