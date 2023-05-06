FROM golang:1.20.3-bullseye

WORKDIR /phone-book-boost/srv_consumer

COPY go.mod go.sum ./
COPY Makefile ./
COPY *.go ./

RUN make dep && make build
RUN make vet

CMD ["./srv_consumer"]
