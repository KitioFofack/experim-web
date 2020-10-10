#!/bin/bash

#back to home
cd ~

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

#merge master branch

#install maven
echo "----------> Install maven"
sudo apt-get install -y maven

#enter into the project directory
cd ~/experim-web/

#create jar package
echo "-----------> Create jar package"
mvn package

#remove image if exist
echo "-------->remove existing image, no be affraid by error"
docker rmi experim:v1

#build image
echo "--------> build docker image"
docker build -t experim:v1 .

#run docker
echo "--------> run docker"
docker run -tid --name experim-docker -p 8080:8080 experim:v1

#launch to nav
echo "---------->run http://$hostname:8080/ on browser"
