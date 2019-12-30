#!/bin/bash

export POSTGRES_DATA_DIR=/var/postgres/data
sudo mkdir -p $POSTGRES_DATA_DIR
export PGADMIN_SERVERS_JSON=/var/postgres/servers.json
if [ ! -f "$PGADMIN_SERVERS_JSON" ]; then
  echo "$PGADMIN_SERVERS_JSON does not exists"
  exit 1
fi

sbt clean universal:packageXzTarball
[ $? -eq 0 ] || exit 1
docker login
docker rm -f meugeninua.horoscopes meugeninua.postgres meugeninua.pgadmin
docker build -t meugeninua.horoscopes:1.0 --force-rm .

# docker container run --name meugeninua.postgres -e POSTGRES_PASSWORD=horoscopes -e POSTGRES_USER=horoscopes -e POSTGRES_DB=horoscopes --mount "type=bind,src=$POSTGRES_DATA_DIR,dst=/var/lib/postgresql/data" --network horoscopes-network -d postgres:9.6
# docker container run --name meugeninua.horoscopes --network horoscopes-network -d -p 9000:9000 meugeninua.horoscopes:1.0
# docker container run --name meugeninua.pgadmin -e PGADMIN_DEFAULT_EMAIL=pgadmin@example.com -e PGADMIN_DEFAULT_PASSWORD=welc0me -e PGADMIN_LISTEN_PORT=5480 -v $PGADMIN_SERVERS_JSON:/pgadmin4/servers.json --network horoscopes-network -d -p 5480:5480 dpage/pgadmin4:latest
docker-compose up -d