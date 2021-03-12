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

3- clone the repositories in the same directory
 git clone https://github.com/KitioFofack/experim-web.git
 git clone https://gitface-prod.irex.aretex.ca/irex-docker-projects/mirroting-iaas-projects/authenticatin/step-ca-client.git

4- checkout to the rest_template_docker_compose branch
 git checkout rest_template_docker_compose

5- go to experim-web directory, modify the application.properties in src/main/resources/ with the right url and use the credentials
 cd experim-web
 vim src/main/resources/application.properties
 erpnextServerURLurl= http://capetc.dev.irex.aretex.ca
 erpnextAccount=xx@xxx
 erpnextAccountPassword=xxx

7- go to the step-ca directory and set default conf values like shown here: https://tuleap.irex.aretex.ca/plugins/mediawiki/wiki/k8s-iaas/index.php?title=Use_client_script_for_make_automatically_certificate
 cd ../step-ca-client

8 - Make sure dns resolution is working on your server 
 sudo apt install dnsutils // install dns tools
 dig $HOSTNAME 
 or 
 nslookup $HOSTNAME // should return a results containing your hostname and your ip address
The commands bellow will show your hostname
 echo $HOSTNAME // show your hostname
 hostname -i // show your ip address
Make sur resolution is ok before the next step

9- go back to the experim-web folder and, run as root user the script deployWithDocker.sh(uncomment some lines if needed)
 cd ../experim-web
 chmod +x deployWithDocker.sh
 sudo ./deployWithDocker.sh
10- go to your favorite navigator at this address http(s)://your_hostname to launch with the ip-address

11- you can test your leap(prospect) creation using the url and credential at step 5
