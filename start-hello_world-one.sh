#!/bin/bash

HOST=localhost
PORT=8090
DATA='{}'

curl -s -u executionUser:executionUser  \
-H "accept: application/json" \
-H "content-type: application/json" \
-X POST \
"http://$HOST:$PORT/rest/server/containers/kcontainer/processes/hello_world/instances" \
-d "$DATA"

