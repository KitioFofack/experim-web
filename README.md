# experim-web
experim portail

[docker deployment]

This is done on debian 10


1- install docker and docker-compose
 docker installation link : https://docs.docker.com/engine/install/
then
 docker-compose installation link : https://docs.docker.com/compose/install/
2- install git
 sudo apt install git-all
3- clone the repository 
 git clone https://github.com/KitioFofack/experim-web.git
4- checkout to the rest_template_docker_compose branch
 git checkout rest_template_docker_compose
5- modify the application.properties in src/main/resources/ with the right url and use the credentials
 erpnextServerURLurl= http://capetc.dev.irex.aretex.ca
 erpnextAccount=testexperim@irex.aretex.ca
 erpnextAccountPassword=Test@123
6 - Make sure dns resolution is working on your server 
 sudo apt install dnsutils // install dns tools
 dig $HOSTNAME 
 or 
 nslookup $HOSTNAME // should return a results containing your hostname and your ip address
The commands bellow will show your hostname
 echo $HOSTNAME // show your hostname
 hostname -i // show your ip address
Make sur resolution is ok before the next step

7- run as root user the script deployWithDocker.sh(uncomment some lines if needed)
 chmod +x deployWithDocker.sh
 sudo ./deployWithDocker.sh
8- go to your favorite navigator at this address http(s)://your_hostname to launch with the ip-address

9- you can test your leap(prospect) creation using the url and credential at step 5
