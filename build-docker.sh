#!/bin/bash

sbt clean universal:packageXzTarball
[ $? -eq 0 ] || exit 1
sudo docker login
sudo docker build -t meugeninua.horoscopes --force-rm .
