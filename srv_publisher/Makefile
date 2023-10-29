COMPOSE_TESTS_PATH := app/tests/docker-compose.tests.yaml

run-on-docker:
	docker-compose -f local/docker-compose.service.yaml up -d

run-rabbitmq:
	docker pull rabbitmq:3-management-alpine
	docker run --pull always --rm -d --name my-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management-alpine

run-tests-docker:
	docker build --target test -t test .
	docker-compose -f $(COMPOSE_TESTS_PATH) up --build -d
	docker-compose -f $(COMPOSE_TESTS_PATH) exec -T rabbitmq sh -c "while ! nc -z rabbitmq 5672; do sleep 1; done"
	docker exec sut sh -c "pytest"
	docker-compose -f $(COMPOSE_TESTS_PATH) down