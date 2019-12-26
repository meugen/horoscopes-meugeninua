#!/bin/bash

sbt clean universal:packageXzTarball
[ $? -eq 0 ] || exit 1
sudo docker login
sudo docker rm -f meugeninua.horoscopes
sudo docker rm -f meugeninua.postgres
sudo docker build -t meugeninua.horoscopes:1.0 --force-rm .

sudo docker container run --name meugeninua.postgres -e POSTGRES_PASSWORD=horoscopes -e POSTGRES_USER=horoscopes -e POSTGRESS_DB=horoscopes -v postgre-data:/var/lib/postgresql/9/main --network horoscopes-network -d postgres:9.6
sudo docker container run --name meugeninua.horoscopes --network horoscopes-network -d -p 9000:9000 meugeninua.horoscopes:1.0