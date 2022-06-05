#!/bin/bash

docker build -t registry.gitlab.com/whitehatspwr/bts-totc-challenge:latest .
docker tag registry.gitlab.com/whitehatspwr/bts-totc-challenge:latest docker.io/whitehatspwr/bts-totc-challenge:latest

docker push registry.gitlab.com/whitehatspwr/bts-totc-challenge:latest
docker push docker.io/whitehatspwr/bts-totc-challenge:latest