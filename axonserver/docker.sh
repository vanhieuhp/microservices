#!/bin/bash

docker run -d --name axonserver \
-p 8024:8024 -p 8124:8124 \
-v "./data":/axonserver/data \
-v "./logs":/axonserver/logs \
-v "./config":/axonserver/config \
--restart unless-stopped \
axoniq/axonserver:latest