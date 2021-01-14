#!/bin/bash

#back to home
#cd ~

## Install git
#apt-get install git -y
## install openssl
apt-get install -y openssl certbot

##rmove all directory
#rm -rf ~/experim-web
##clone the repository
#echo "-----> clone the repo"
#git clone https://github.com/KitioFofack/experim-web.git
#cd ~/experim-web

#checkout to main branch
#git checkout rest_template_docker_compose

#install maven
echo "----------> Install maven"
apt-get install -y maven

#enter into the project directory
#cd ~/experim-web/

source conf.defaults

#create jar package
echo "-----------> Create jar package"
mvn clean package

#remove image if exist
echo "-------->remove existing image, no be affraid by error"
docker rmi -f experim:v1
docker rm -f experim-docker
docker rmi -f nginx:1.15-alpine
docker rm -f webserver

#build image
echo "--------> build docker image"
docker build -t experim:v1 .

##Key generation
echo "--------> generating key"
openssl genrsa -aes256 -passout pass:$PASSWORD -out $HOSTNAME.key 4046
##Remove pem phrase from the key
openssl rsa -in ./$HOSTNAME.key -passin pass:$PASSWORD -out $HOSTNAME.key

##Creating a certificate
openssl req -key ./$HOSTNAME.key -new -x509 -days 365 -out $HOSTNAME.crt -passin pass:$PASSWORD -subj "/C=$COUNTRY/ST=$STATE/L=$LOCALITY/O=$ORGANIZATION/OU=$ORGANIZATIONALUNIT/CN=$HOSTNAME/emailAddress=$EMAIL"
mv $HOSTNAME.key $HOSTNAME.crt ./docker-compose-data/certbot

##setting up nginx configuration file
chmod +x ./nginx_exp_conf.sh
source ./nginx_exp_conf.sh

#run docker compose
echo "--------> running docker compose"
cd docker-compose-data
docker-compose up

echo "---------->bye bye"
