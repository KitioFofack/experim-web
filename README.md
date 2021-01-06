# experim-web
experim portail

[docker deployment]
This deployment as been done on debian 10 with docker and docker compose installed
1- install git
2- clone the repository at https://github.com/KitioFofack/experim-web.git
3- checkout to the rest_template_docker_compose branch
4- modify the file in docker-compose-data/nginx/experim.conf for nginx to match your server
5- modify the application.properties in src/main/resources/ with the right url et utilisé le login testexperim@irex.aretex.ca/Test@123
6- run as root user the script deployWithDocker.sh(uncomment some lines if needed)
7- go to your favorite navigator at this address http(s)://$HOSTNAME/
