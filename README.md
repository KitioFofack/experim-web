Follow this steps on your linux server(debian 10 is this case) for others you need to do few changes

1- install docker and docker-compose
 docker installation link : https://docs.docker.com/engine/install/
then
 docker-compose installation link : https://docs.docker.com/compose/install/

2- install git
 sudo apt install git


3- clone the repository 
 git clone https://github.com/KitioFofack/experim-web.git

4- go to experim-web directory checkout to the rest_template_docker_compose branch
 cd experim-web
 git checkout rest_template_docker_compose
 
5- modify the conf.defaults  with the right url and user the credentials
 vim conf.defaults
 ERP_SERVER_URL= http://capetc.dev.irex.aretex.ca (http://capetc.uat.irex.aretex.ca for uat deployment and erpnext.capetc.aretex.ca for prod)
The email address and the password for experim to log in to your ERP server, those bellow are made for developement envirenment and uat environment
 ERP_USER_EMAIL=xx@xx
 ERP_USER_PASSWORD=xxx

6- go to the step-ca directory and set default conf values like shown here: https://tuleap.irex.aretex.ca/plugins/mediawiki/wiki/k8s-iaas/index.php?title=Use_client_script_for_make_automatically_certificate
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

