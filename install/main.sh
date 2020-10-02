#!bin/bash

#calling script for set env and generate .war files
./1-create-war.sh

#calling scritp for build images and run containers
./2-dockerize.sh


