#!/bin/bash

HOST=localhost
PORT=8090
DATA='{}'

for (( c=1; c<=5; c++ ))
do
  echo $c

  curl -s -u executionUser:executionUser  \
        -H "accept: application/json" \
        -H "content-type: application/json" \
        -X POST \
        "http://$HOST:$PORT/rest/server/containers/kcontainer/processes/hello_world/instances" \
        -d "$DATA" 2>&1 &
  sleep 5
done

