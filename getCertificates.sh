#!/bin/bash


###get certificates from client
CURRENT_DIR=$(pwd)

source conf.defaults

echo "clone the step-client repo.."
git clone $URL_REPO $HOME/step-ca-client
echo "end clone repo.."

echo "enter to the directory..."
cd $HOME/step-ca-client

echo "modify the default.conf.."

sed -i.bak "s#step-ca.dev.irex.aretex.ca#$STEP_CA_HOST#g" $HOME/step-ca-client/Conf/conf.defaults
sed -i "s#test-step.dev.irex.aretex.ca#$STEP_CLIENT_NAME#g" $HOME/step-ca-client/Conf/conf.defaults
sed -i "s#192.168.33.203#$STEP_CA_IP#g" $HOME/step-ca-client/Conf/conf.defaults
sed -i "s#8443#$PORT_ACCESS#g" $HOME/step-ca-client/Conf/conf.defaults
sed -i "s#STEP_CA_USER=\"debian\"#STEP_CA_USER=\"$STEP_CA_USER\"#g" $HOME/step-ca-client/Conf/conf.defaults
sed -i "s#STEP_CA_PASS_USER=\"debian\"#STEP_CA_PASS_USER=\"$STEP_CA_PASS_USER\"#g" $HOME/step-ca-client/Conf/conf.defaults

echo "run main client script.."
./MainScript.sh
echo "end run main script client.."

cd $CURRENT_DIR
