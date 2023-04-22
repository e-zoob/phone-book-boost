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
	$(PIP) install httpx install httpx
	$(PIP) install pytest	
	$(PIP) install -r requirements.txt

test: install
	$(PYTEST)

clean:
	rm -rf $(VENV) __pycache__ .pytest_cache
	
run-test-docker:
	docker build --target test -t test-img .
