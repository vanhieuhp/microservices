# Infrastructure

This directory contains the infrastructure configuration and resources for the microservices project. It is organized into several subfolders, each serving a specific purpose in managing and deploying the system.

## Structure

- **kubernetes/**  
  Contains Kubernetes manifests and configuration files for deploying and managing services in a Kubernetes cluster.  
  Typical contents: Deployment, Service, Ingress, ConfigMap, and Secret YAML files.  
  **Details:**  
  - **Deployment:** Defines how to deploy your application, including the number of replicas and container images.  
  - **Service:** Exposes your application to the network, allowing it to be accessed by other services or users.  
  - **Ingress:** Manages external access to the services, typically HTTP.  
  - **ConfigMap:** Stores non-confidential configuration data in key-value pairs.  
  - **Secret:** Stores sensitive information, such as passwords and tokens.

- **config_properties/**  
  Stores configuration property files used by various services or infrastructure components.  
  Typical contents: `.env` files, application property files, or other configuration formats.  
  **Details:**  
  - **.env files:** Environment-specific configuration files that can be used to set environment variables.  
  - **Application properties:** Configuration files for specific applications, often in YAML or JSON format.

- **docker_compose/**  
  Contains Docker Compose files for local development and testing.  
  Typical contents: `docker-compose.yml` and override files for orchestrating multi-container Docker applications.  
  **Details:**  
  - **docker-compose.yml:** Defines the services, networks, and volumes for your application.  
  - **Override files:** Additional configuration files that can be used to override settings in the main `docker-compose.yml`.

## Usage

### 1. Kubernetes

To deploy services to a Kubernetes cluster:

```bash
cd kubernetes
kubectl apply -f .
```

> Make sure you have `kubectl` configured for your target cluster.

### 2. Config Properties

Update or add configuration files as needed for your services.  
These files may be mounted as volumes or injected as environment variables depending on your deployment method.

### 3. Docker Compose

To start the services locally using Docker Compose:

```bash
cd docker_compose
docker-compose up -d
```

> Ensure you have Docker and Docker Compose installed.

## Notes

- Keep sensitive information (such as passwords or API keys) out of version control. Use environment variables or secret management tools where possible.
- Update this README as the infrastructure evolves. 