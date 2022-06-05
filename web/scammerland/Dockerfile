FROM node:current-buster-slim

RUN apt-get update \
    && apt-get install -y supervisor chromium --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY challenge/package.json .

RUN yarn

COPY challenge .

COPY flag.txt /tmp/flag.txt

COPY supervisord.conf /etc/supervisord.conf

EXPOSE 80

ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
