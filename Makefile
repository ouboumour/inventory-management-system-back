.PHONY: help test install build clean run build-image run-image deploy-image frontend-image
.DEFAULT_GOAL: help
include .env

help:
	@grep -E '(^[a-zA-Z_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[32m%-10s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

test:
	@mvn test

install:
	@mvn dependency:resolve

build: install
	@mvn clean package

clean:
	@mvn clean

run:
	@mvn spring-boot:run

build-image: build
	@docker build --tag=$(IMAGE_BACKEND) .

run-image: build-image
	@docker run -it --rm $(IMAGE_BACKEND)

deploy-image: build-image
	@docker push $(IMAGE_BACKEND)

frontend-image:
	@docker pull $(IMAGE_BACKEND)
	@docker run -it --rm $(IMAGE_BACKEND)
