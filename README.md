# experim-web
experim portail

[docker deployment]

This is done on debian 10

1- install docker and docker-compose
2- install git
3- clone the repository at https://github.com/KitioFofack/experim-web.git
4- checkout to the rest_template_docker_compose branch
5- modify the application.properties in src/main/resources/ with the right url and use the credentials testexperim@irex.aretex.ca/Test@123
6- run as root user the script deployWithDocker.sh(uncomment some lines if needed)
7- go to your favorite navigator at this address http(s)://$HOSTNAME/ or lauch with the ip-address
