#!/bin/bash

cat > docker-compose-data/nginx/experim.conf << EOF
server {
    listen 80;
    listen [::]:80;
    server_name $HOSTNAME www.$HOSTNAME;

    location / {
        return 301 https://$host$request_uri;
    }
}

server {
    listen 443 ssl http2;
    listen [::]:443 http2;

    server_name $HOSTNAME www.$HOSTNAME;

    location / {
	proxy_pass http://$HOSTNAME:8080/;
        proxy_set_header Host $host;
	proxy_set_header X-Real-IP $remote_addr;
	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
	proxy_set_header X-Forwarded-Host $server_name;
    }

    ssl_certificate /etc/ssl/nginx/$HOSTNAME.crt;
    ssl_certificate_key /etc/ssl/nginx/$HOSTNAME.key;


}
EOF