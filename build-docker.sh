#!/bin/bash

sbt clean universal:packageXzTarball
sudo docker build -t meugeninua.horoscopes --force-rm .
