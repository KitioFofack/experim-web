#!/bin/bash

##update package repo
sudo apt-get update -y


#install maven
echo "----------> Install maven"
sudo apt-get install -y maven

#enter into the project directory
#cd ~/experim-web/
sudo apt-get -y install dnsutils

sudo chown -R $USER: .

source getCertificates.sh

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
sudo docker rmi -f experim:v1
sudo docker rm -f experim-docker
sudo docker rmi -f nginx:1.15-alpine
sudo docker rm -f webserver

#build image
echo "--------> build docker image"
sudo docker build -t experim:v1 .

##Key generation
echo "--------> generating key"

#cd ../step-ca-client
#./MainScript.sh 2>&1 | tee install.log

#cd ../experim-web

mkdir docker-compose-data/certbot
sudo cp /etc/letsencrypt/live/$HOSTNAME/fullchain.pem ./docker-compose-data/certbot
sudo cp /etc/letsencrypt/live/$HOSTNAME/privkey.pem ./docker-compose-data/certbot

##setting up nginx configuration file
chmod +x nginx_exp_conf.sh
source ./nginx_exp_conf.sh

#run docker compose
echo "--------> running docker compose"
cd docker-compose-data
sudo docker-compose up

echo "---------->bye bye"
