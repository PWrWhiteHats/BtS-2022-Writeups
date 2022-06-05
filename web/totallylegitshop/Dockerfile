FROM wordpress:php7.4-apache

RUN apt-get update \
    && apt-get install -y supervisor mariadb-server --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

COPY challenge/config/custom.ini $PHP_INI_DIR/conf.d/

ADD challenge/html.tar.gz /var/www/

RUN chown -R www-data:www-data /var/www

COPY challenge/sql/ /tmp/

COPY flag.txt /tmp/flag.txt 

RUN sed -i "s/FLAG/`cat /tmp/flag.txt`/g" /tmp/db.sql && rm /tmp/flag.txt

COPY challenge/mysql-entrypoint.sh /usr/bin/

RUN chmod +x /usr/bin/mysql-entrypoint.sh 

ENV WORDPRESS_SITE_URL localhost

COPY supervisord.conf /etc/supervisord.conf

EXPOSE 80

ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]