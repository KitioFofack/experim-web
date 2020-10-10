#!/bin/bash

#back to home
cd ~

## Install git 
sudo apt install git -y

##rmove all directory
rm -rf ~/experim-web

#clone the repository
echo "-----> clone the repo"
git clone https://github.com/KitioFofack/experim-web.git
cd ~/experim-web

#checkout to main branch
git checkout main

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

#create jar package
echo "-----------> Create jar package"
mvn clean package

#remove image if exist
echo "-------->remove existing image, no be affraid by error"
docker rmi -f experim:v1
docker rm -f experim-docker

#build image
echo "--------> build docker image"
docker build -t experim:v1 .

#run docker
echo "--------> run docker"
docker run -tid --name experim-docker -p 8080:8080 experim:v1

#launch to nav
echo "---------->run http://$hostname:8080/ on browser"
