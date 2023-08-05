PYTHON := python3
VENV := venv
PIP := $(VENV)/bin/pip
ACTIVATE := . $(VENV)/bin/activate
PYTEST := $(VENV)/bin/pytest

.PHONY: venv install test clean

venv:
	$(PYTHON) -m venv $(VENV)
	$(ACTIVATE)

install: venv
	$(PYTHON) -m pip install --upgrade pip
	$(PIP) install -r requirements.txt

install-test-dep:
	$(PIP) install -r test-requirements.txt

clean:
	rm -rf $(VENV) __pycache__ .pytest_cache

run-on-docker:
	docker-compose -f local/docker-compose.service.yaml up -d

run-all-tests-compose:
	docker build --target test -t test .
	docker-compose -f app/tests/docker-compose.tests.yaml up --build -d 
	docker-compose -f app/tests/docker-compose.tests.yaml exec -T rabbitmq sh -c "while ! nc -z rabbitmq 5672; do sleep 1; done"
	docker exec sut bash -c "pytest"
	docker-compose -f app/tests/docker-compose.tests.yaml down

run-test-docker:
	docker stop pytest || true && \
	docker build --target test -t pytest-img . && \
	docker run -it -d --rm --name pytest pytest-img && \
	docker exec pytest sh -c "pytest" || true