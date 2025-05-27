# Set base directory
BASE_DIR := $(shell pwd)

# Services
GRADLE_SERVICES := accounts products
MAVEN_SERVICES := cards loans

# ========== TASKS ==========

.PHONY: all bootJar build deploy

all: bootJar build

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
		docker build -t $$service:1.0 $(BASE_DIR)/$$service; \
	done

deploy:
	@echo "Deploying services..."
	# Add your deployment steps here. Example:
	@docker-compose up -d
	# kubectl apply -f k8s/
