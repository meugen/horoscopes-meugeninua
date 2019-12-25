#!/bin/bash

sbt clean universal:packageXzTarball
sudo docker login
sudo docker build -t meugeninua.horoscopes --force-rm .
