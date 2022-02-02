.PHONY: help test install build clean run build-image run-image deploy-image frontend-image
.DEFAULT_GOAL: help
include .env

help:
	@grep -h -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-${HELP_FIRST_COLUMN_SIZE}s\033[0m %s\n", $$1, $$2}'

test: # Launcer les tests via maven
	@mvn test

install: # Installer les dépendances via maven
	@mvn dependency:resolve

build: install # Build le projet via maven
	@mvn clean package

clean: # Clean le build via maven
	@mvn clean

run: # Lancer l'application
	@mvn spring-boot:run

build-image: build # Créer une image docker de l'application
	@docker build --tag=$(IMAGE_BACKEND) .

run-image: build-image # Lancer un container de l'application
	@docker run -it -p 8080:8080 --rm $(IMAGE_BACKEND)

deploy-image: build-image # Déployer une image vers le repository/backend
	@docker push $(IMAGE_BACKEND)

frontend-image: # Importer une image frontend de la repository et la lancer
	@docker pull $(IMAGE_FRONTEND)
	@docker run -it --rm $(IMAGE_FRONTEND)
