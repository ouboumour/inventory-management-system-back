.PHONY: install run test build build-image run-image deploy-image frontend-image

install:
	mvn dependency:resolve

run:
	mvn clean package
	java -jar ./target/backend-0.0.1-SNAPSHOT.jar

test:
	mvn test

build:
	mvn clean package

build-image: build
	docker build --tag=safwanus/demenagement-backend .

run-image: build-image
	docker run -it safwanus/demenagement-backend

deploy-image: build-image
	docker push safwanus/demenagement-backend

frontend-image:
	docker pull safwanus/demenagement-frontend
	docker run -it safwanus/demenagement-frontend
