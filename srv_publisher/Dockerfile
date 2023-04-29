FROM python:3.10 AS build
WORKDIR /phone-book-boost/srv_publisher
COPY ./requirements.txt /phone-book-boost/srv_publisher/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /phone-book-boost/srv_publisher/requirements.txt
COPY ./app /phone-book-boost/srv_publisher/app

FROM build AS test
RUN pip install pytest \ pip install httpx
RUN pytest

FROM build AS final
WORKDIR /phone-book-boost/srv_publisher
COPY --from=build /phone-book-boost/srv_publisher/app ./
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "80"]
