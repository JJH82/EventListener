#!/bin/bash
if [ -z "$1" ]
then
    echo "첫번째 파라미터값 공백이거나 null 입니다!"
    exit 1
if

ps -ef | grep java | grep $1.jar

DAEMON_PID=`ps -ef | grep java | grep $1.jar | awk '{print $2}'`
echo "kill -9 $DAEMON_PID"
kill -9 $DAEMON_PID