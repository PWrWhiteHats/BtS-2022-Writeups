#!/bin/bash

site_url=${WORDPRESS_SITE_URL:-localhost}

mysqld_safe &

echo "Wait for a while"

while ! mysql -e "select NOW(); " &>/dev/null; do
	sleep 1
done

echo "MYSQL server created. Waiting for databases"

sed -i "s/SITE_URL/$site_url/g" /tmp/wordpress.sql

cat /tmp/db.sql /tmp/wordpress.sql > /tmp/init.sql
mysql -u root < /tmp/init.sql
while ! mysql -e "use wordpress; select NOW() from wp_ctf_commentmeta;" 2>/dev/null; do
	sleep 1
done

echo "Databases created"

docker-entrypoint.sh $@ 