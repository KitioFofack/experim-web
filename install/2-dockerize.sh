#!bin/bash

#create dockerfile
echo "creating dockerfile for build image---------"

cat << EOF > ~/experim-web/Dockerfile
FROM tomcat:latest
ADD target/experim-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
EOF

#create docker-compose.yml
echo "creating docker-compose.yml for up all docker"

cat << EOF > ~/experim-web/docker-compose.yml
version: '3'

services:
 app:
  build: .
  image: experim-web
  ports:
   - 8081:8080
EOF

#launch and build images
cd ~/experim-web

#build images and run docker
echo "run and build images----------------------"
docker-compose up --build 2>&1 | tee ~/log-deploy-experim/build_images.log


echo "your application accessible at http://dockerpaas-dev.irex.aretex.ca:8081/experim-0.0.1-SNAPSHOT/"

