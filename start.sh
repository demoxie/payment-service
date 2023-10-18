#!/bin/bash
APP_NAME=payment-service
sudo nohup java -jar target/payment-service-0.0.1-SNAPSHOT.jar > logs/payment-service-logs.txt 2>&1 &
if [ -n "$!" ]; then
    sudo sh -c echo $! > pid/pid.txt
    echo "Process ID $! has been saved to pid/pid.txt"
    echo "$APP_NAME is up and running"
else
    echo "$APP_NAME is not running"
fi
