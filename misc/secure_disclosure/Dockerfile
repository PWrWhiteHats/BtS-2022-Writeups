FROM nginx:1.22.0-alpine

WORKDIR /usr/share/nginx/html

RUN apk update && apk add --no-cache supervisor && rm -rf /var/cache/apk/*

COPY supervisord.conf /etc/supervisord.conf

COPY challenge/conf/nginx.conf /etc/nginx/nginx.conf
COPY challenge/conf/default.conf /etc/nginx/conf.d/default.conf
COPY challenge/conf/default.conf /etc/nginx/conf.d/default.conf

COPY challenge/*.txt challenge/*.html /usr/share/nginx/html/

EXPOSE 80

ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]