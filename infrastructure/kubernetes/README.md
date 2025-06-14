# Install keycloak with helm chart 
1. helm repo add bitnami https://charts.bitnami.com/bitnami
2. helm upgrade --install keycloak bitnami/keycloak \
  --values values/keycloak.yaml

helm install keycloak bitnami/keycloak \
--version 24.4.2 \
--namespace keycloak \
--create-namespace \
--values keycloak-values.yaml