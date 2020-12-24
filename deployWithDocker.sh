#!/bin/bash

#back to home
cd ~

## Install git
apt-get install git -y
## install openssl
apt-get install -y openssl certbot

##rmove all directory
rm -rf ~/experim-web
##clone the repository
echo "-----> clone the repo"
git clone https://github.com/KitioFofack/experim-web.git
cd ~/experim-web

#checkout to main branch
git checkout rest_template_docker_compose

#create dockerfile
echo "-----------> Create dockerfile"
cat << EOF > ~/experim-web/Dockerfile
FROM openjdk:11
VOLUME /tmp
ADD target/experim-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EOF


#install maven
echo "----------> Install maven"
sudo apt-get install -y maven

#enter into the project directory
cd ~/experim-web/

source conf.defaults

#create jar package
echo "-----------> Create jar package"
mvn clean package

#remove image if exist
echo "-------->remove existing image, no be affraid by error"
docker rmi -f experim:v1
docker rm -f experim-docker
docker rmi -f nginx:1.15-alpine
docker rm -f webserver_exp

#build image
echo "--------> build docker image"
docker build -t experim:v1 .

##Key generation
echo "--------> generating key"
openssl genrsa -aes256 -passout pass:$PASSWORD -out $HOSTNAME.Key 4046
##Remove pem phrase from the key
openssl rsa -in ./$HOSTNAME.key -passin pass:$PASSWORD -out $HOSTNAME.key

##Creating a certificate
openssl req rsa -key ./$HOSTNAME.key -new -x509 -days 365 -out $HOSTNAME.crt -passin pass:$PASSWORD -subj "/C=$COUNTRY/ST=$STATE/L=$LOCALITY/O=$ORGANIZATION/OU=$ORGANIZATION_UNIT/CN=$DOMAIN/emailAddress=$EMAIL"
mv $HOSTNAME.key $HOSTNAME.crt ./docker-compose-data/certbot

#run docker compose
echo "--------> running docker compose"
cd docker-compose
docker-compose up

#launch to nav
echo "---------->run http://$hostname:8080/ on browser"
