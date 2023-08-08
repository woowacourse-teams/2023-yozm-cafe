#!/bin/bash

echo Starting MySQL

if [ "`mysql -u'$MYSQL_USER' -p'$MYSQL_PASSWORD' -se'USE $MYSQL_DATABASE;' 2>&1`" != "" ]; then
  echo database not exists, clone from remote ...
  mysqldump -P 3306 -h remote-mysql -u root --password="$MYSQL_ROOT_PASSWORD" $MYSQL_DATABASE 1> /docker-entrypoint-initdb.d/init.sql
fi

exec /entrypoint.sh "$@"
