play-with-codespaces
--------------------

### Start All Servers

```bash
sudo service redis-server start
sudo service rabbitmq-server start
sudo service mysql start
```

| Service |  URI | Default Account |
| ------- |  --- | --------------- |
| Redis | 127.0.0.1:6379 | - (no auth, empty string) |
| RabbitMQ | 127.0.0.1:5672 | guest:guest |
| MySQL | localhost:3306 | root:"" (password is empty, you need to modify it) |

### Access to Redis

```bash
$ redis-cli
127.0.0.1:6379> 
```

### Access to RabbitMQ

You can enable `rabbitmq_management` plugin then farward local `15672` port, visit codespaces local address link, then management online.

```bash
$ sudo rabbitmq-plugins enable rabbitmq_management
```

### Access to MySQL

You should using `sudo mysql -uroot` command to login in the MySQL server. Then we reate a new user with database.

```bash
$ sudo mysql -uroot
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 54
Server version: 10.3.34-MariaDB-0+deb10u1 Debian 10

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
MariaDB [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
+--------------------+
3 rows in set (0.000 sec)
MariaDB [(none)]>CREATE DATABASE `t_blog` DEFAULT CHARSET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;
MariaDB [(none)]>ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
MariaDB [(none)]>flush privileges;
```

### Setting system environments

```bash
$ vim ~/.bashrc
# add exports below
export MYSQL_DB_HOST=localhost MYSQL_DB_PORT=3306 MYSQL_DB_USER=root MYSQL_DB_PASSWORD=root
export REDIS_HOST=localhost REDIS_PORT=6379 REDIS_PASSWORD=
export RABBITMQ_HOST=127.0.0.1 RABBITMQ_PORT=5672 RABBITMQ_USER=guest RABBITMQ_PASSWORD=guest
$ source ~/.bashrc
```

### Running

Using `blog-web` project as example:

```bash
$ cd blog-web
$ mvn clean install
$ java -jar ./target/blog-web-0.0.1-SNAPSHOT.jar
```

