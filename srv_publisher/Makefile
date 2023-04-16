PYTHON := python3
VENV := venv
PIP := $(VENV)/bin/pip
ACTIVATE := . $(VENV)/bin/activate
PYTEST := $(VENV)/bin/pytest

.PHONY: help venv install test clean

.DEFAULT_GOAL = help

help:
	@echo "Available targets:"
	@echo "  venv      - Create a virtual environment"
	@echo "  install   - Install dependencies"
	@echo "  test      - Run tests"
	@echo "  clean     - Clean the workspace"

venv:
	$(PYTHON) -m venv $(VENV)

install: venv
	$(PYTHON) -m $(PIP) install --upgrade pip
	$(PIP) install httpx install httpx
	$(PIP) install pytest	
	$(PIP) install -r requirements.txt

test: install
	$(ACTIVATE) && $(PYTEST)

clean:
	rm -rf $(VENV) __pycache__ .pytest_cache
