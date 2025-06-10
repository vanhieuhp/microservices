-- Create the database (if it doesn't exist)
CREATE DATABASE IF NOT EXISTS grafana;

-- Create the user with a password
CREATE USER 'grafana_user'@'%' IDENTIFIED BY 'secretpassword';

-- Grant all privileges on the grafana database to the user
GRANT ALL PRIVILEGES ON grafana.* TO 'grafana_user'@'%';

-- Apply the changes
FLUSH PRIVILEGES;
