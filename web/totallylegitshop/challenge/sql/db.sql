SET GLOBAL query_cache_size = 1000000;
CREATE USER 'mysql_hard_username'@'%' IDENTIFIED BY 'p@sswrD_4_mysql_instance';
CREATE USER 'FLAG'@'localhost' IDENTIFIED BY '2c73bdccfcb396e58ede6691fb9ca773';
CREATE DATABASE IF NOT EXISTS wordpress CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL PRIVILEGES ON wordpress.* TO 'mysql_hard_username'@'%';
GRANT SELECT ON mysql.* TO 'mysql_hard_username'@'%';

USE wordpress;