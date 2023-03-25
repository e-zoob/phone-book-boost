FROM python:3.10

WORKDIR /phone-book-boot/http

COPY ./requirements.txt /phone-book-boot/http/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /phone-book-boot/http/requirements.txt

COPY ./app /phone-book-boot/http/app

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "80"]