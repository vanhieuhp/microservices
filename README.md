# Microservices Project

## Overview

This project is a microservices-based application designed to provide various financial services, including account management, card operations, loan processing, and messaging. Each service is built using Spring Boot and is designed to be scalable, maintainable, and independently deployable. The architecture leverages Spring Cloud for service discovery, configuration management, and API gateway functionalities, ensuring a robust and efficient system.

## Services

- **accounts**: 8001
- **cards**: 8002
- **loans**: 8003
- **messages**: 8021
- **configserver**: 8071
- **eurekaserver**: 8761
- **gatewayserver**: 8072

## Authentication

- **keycloak**: 8015

## Monitoring

- **grafana**: 3000
- **prometheus**: 9100
- **tempo**: 4317

## Databases

- **mysql**: 3306
- **rabbitmq**: 5672
- **redis**: 6397

## Microservices Description

### Accounts Service
- **Functionality:** Manages user accounts, including registration, login, and account details.
- **Technologies:** Spring Boot, Spring Security, JPA.

### Cards Service
- **Functionality:** Handles credit and debit card operations, such as issuance, transactions, and balance inquiries.
- **Technologies:** Spring Boot, Spring Data JPA.

### Loans Service
- **Functionality:** Manages loan applications, approvals, and repayments.
- **Technologies:** Spring Boot, Spring Cloud.

### Messages Service
- **Functionality:** Handles messaging and notifications between services.
- **Technologies:** Spring Boot, RabbitMQ.

### Config Server
- **Functionality:** Centralizes configuration management for all microservices.
- **Technologies:** Spring Cloud Config.

### Eureka Server
- **Functionality:** Provides service discovery and registration for microservices.
- **Technologies:** Spring Cloud Netflix Eureka.

### Gateway Server
- **Functionality:** Acts as an API gateway, routing requests to appropriate microservices.
- **Technologies:** Spring Cloud Gateway.

## Getting Started

### Prerequisites

- Docker and Docker Compose installed
- Kubernetes cluster (if deploying to Kubernetes)
- Helm 3.x installed (for Helm deployment)

### Running the Services

1. **Clone the repository:**

   ```bash
   git clone <repository-url>
   cd microservices
   ```

2. **Start the services using Docker Compose:**

   ```bash
   docker-compose up -d
   ```

3. **Access the services:**

   - Accounts: http://localhost:8001
   - Cards: http://localhost:8002
   - Loans: http://localhost:8003
   - Messages: http://localhost:8021
   - Config Server: http://localhost:8071
   - Eureka Server: http://localhost:8761
   - Gateway Server: http://localhost:8072

### Deploying to Kubernetes

#### Using kubectl

To deploy the services to a Kubernetes cluster, navigate to the `infrastructure/kubernetes` directory and run:

```bash
kubectl apply -f .
```

#### Using Helm Charts

1. **Add the Helm repository (if applicable):**

   ```bash
   helm repo add microservices https://your-helm-repo-url
   helm repo update
   ```

2. **Install the microservices using Helm:**

   ```bash
   # Install all services
   helm install microservices ./infrastructure/helm/microservices

   # Or install individual services
   helm install accounts ./infrastructure/helm/accounts
   helm install cards ./infrastructure/helm/cards
   helm install loans ./infrastructure/helm/loans
   ```

3. **Verify the deployment:**

   ```bash
   helm list
   kubectl get pods
   ```

4. **Upgrade the deployment:**

   ```bash
   helm upgrade microservices ./infrastructure/helm/microservices
   ```

5. **Uninstall the deployment:**

   ```bash
   helm uninstall microservices
   ```

### Helm Chart Configuration

The Helm charts are located in the `infrastructure/helm` directory. Each service has its own chart with the following structure:

```
infrastructure/helm/
├── microservices/           # Parent chart for all services
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates/
├── accounts/               # Individual service chart
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates/
├── cards/
├── loans/
└── ...
```

To customize the deployment, you can:

1. Modify the `values.yaml` file in each chart
2. Override values during installation:

   ```bash
   helm install microservices ./infrastructure/helm/microservices \
     --set accounts.replicaCount=3 \
     --set cards.resources.limits.cpu=500m
   ```

## Notes

- Ensure that all required databases (MySQL, RabbitMQ, Redis) are running and accessible.
- For more detailed information on the infrastructure, refer to the `infrastructure/README.md` file.
- When using Helm, make sure to review and adjust the resource limits and requests in the values.yaml files according to your cluster's capacity.