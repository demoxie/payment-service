#!/bin/bash
APP_NAME=payment-service
pid=`ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}'`

if [ -z "${pid}" ]; then
   echo "$APP_NAME is not running"
else
    echo "killing thread...$pid"
    kill -9 $pid
fi
# Check if there are processes using port 3000
if sudo lsof -ti:3000; then
    # If processes are found, kill them
    sudo kill $(sudo lsof -ti:3000)
    echo "Processes using port 3000 have been killed."
else
    # If no processes are found, print a message
    echo "No processes found using port 3000."
fi