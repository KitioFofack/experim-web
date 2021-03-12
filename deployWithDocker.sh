#!/bin/bash

##update package repo
apt-get update


#install maven
echo "----------> Install maven"
apt-get install -y maven

#enter into the project directory
#cd ~/experim-web/

source conf.defaults

#set values in the application properties' file
WD="src/main/resources/application.properties"
sed -i "s|erpnextServerURL=.*|erpnextServerURL=$ERP_SERVER_URL|g" $WD
sed -i "s|erpNextAccount=.*|erpNextAccount=$ERP_USER_EMAIL|g" $WD
sed -i "s|erpNextAccountPassword=.*|erpNextAccountPassword=$ERP_USER_PASSWORD|g" $WD

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

cd ../step-ca-client
./MainScript.sh 2>&1 | tee install.log

cd ../experim-web
mkdir docker-compose-data/certbot
cp /etc/letsencrypt/live/$HOSTNAME/fullchain.pem ./docker-compose-data/certbot
cp /etc/letsencrypt/live/$HOSTNAME/privkey.pem ./docker-compose-data/certbot

##setting up nginx configuration file
chmod +x nginx_exp_conf.sh
source ./nginx_exp_conf.sh

#run docker compose
echo "--------> running docker compose"
cd docker-compose-data
docker-compose up

echo "---------->bye bye"
