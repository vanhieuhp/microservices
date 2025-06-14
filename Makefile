# Set base directory
BASE_DIR := $(shell pwd)

# Services
GRADLE_SERVICES := accounts cards loans configserver eurekaserver gatewayserver messages
MAVEN_SERVICES :=

# ========== TASKS ==========

.PHONY: all bootJar build deploy publish

all: bootJar build deploy

bootJar:
	@echo "Building JAR files..."
	@for service in $(GRADLE_SERVICES); do \
		echo "-> Building $$service (Gradle)"; \
		cd $(BASE_DIR)/$$service && ./gradlew clean build -x test; \
	done
	@for service in $(MAVEN_SERVICES); do \
		echo "-> Building $$service (Maven)"; \
		cd $(BASE_DIR)/$$service && ./mvnw clean install -DskipTests; \
	done

build:
	@echo "Building Docker images..."
	@for service in $(GRADLE_SERVICES) $(MAVEN_SERVICES); do \
		echo "-> Building Docker image for $$service"; \
		docker rmi $$service:1.0; \
		docker build -t $$service:1.0 $(BASE_DIR)/$$service; \
	done

deploy:
	@echo "Deploying services..."
	# Add your deployment steps here. Example:
	@docker-compose up -d
	# kubectl apply -f k8s/

publish:
	@echo "Publish commonlib to maven local"
	@cd commonlib && ./gradlew clean build -x test && ./gradlew publishToMavenLocal
	@echo "Publish commonlib to maven local successfully!"

tag:
	@echo "Tag Docker images..."
	@for service in $(GRADLE_SERVICES) $(MAVEN_SERVICES); do \
		echo "-> Building Docker image for $$service"; \
		docker tag $$service:1.0 vanhieuit10/easybank:$$service; \
	done

push:
	@echo "Push Docker Image into Registry HUB..."
	@for service in $(GRADLE_SERVICES) $(MAVEN_SERVICES); do \
		echo "-> Building Docker image for $$service"; \
		docker push vanhieuit10/easybank:$$service; \
	done