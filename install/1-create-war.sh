#!bin/bash

#clone the repository
echo "merging main branch to current branch"
git merge main -m "mergin main to current branch"

#create log folder
mkdir ~/log-deploy-experim

#rm -old folder
rm -rf ~/experim-web/target

#install maven
echo "install maven----------------------------------"
sudo apt-get -y install maven 2>&1 | tee ~/log-deploy-experim/install_maven.log

#clean the project
echo "clean project----------------------------------"
mvn clean 2>&1 | tee ~/log-deploy-experim/clean_project.log

#build project for generate war
echo "build project----------------------------------"
mvn install 2>&1 | tee ~/log-deploy-experim/build_project.log

echo "look into target directory the war files-------"


