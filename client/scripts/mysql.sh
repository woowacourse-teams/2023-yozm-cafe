#!/bin/sh

echo Starting MySQL

if [ ! -f "/docker-entrypoint-initdb.d/data.sql" ]; then
  echo Initial data.sql is not exists, clone from remote ...
  mysqldump -P $SOURCE_REMOTE_DB_PORT -h $SOURCE_REMOTE_DB_HOST -u root --password="$MYSQL_ROOT_PASSWORD" $MYSQL_DATABASE 1> /docker-entrypoint-initdb.d/data.sql

  if [[ $? != 0 ]]; then
    rm /docker-entrypoint-initdb.d/data.sql
    echo Clone failed, terminate process
    exit $?
  fi
fi

exec /entrypoint.sh "$@"
