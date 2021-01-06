# experim-web
experim portail

[docker deployment]

This is done on debian 10

1- install docker and docker-compose
2- install git
3- clone the repository at https://github.com/KitioFofack/experim-web.git
4- checkout to the rest_template_docker_compose branch
5- modify the file in docker-compose-data/nginx/experim.conf for nginx to match your server
6- modify the application.properties in src/main/resources/ with the right url and use the credentials testexperim@irex.aretex.ca/Test@123
7- run as root user the script deployWithDocker.sh(uncomment some lines if needed)
8- go to your favorite navigator at this address http(s)://$HOSTNAME/ or lauch with the ip-address
