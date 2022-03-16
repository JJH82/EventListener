#!/bin/bash
if [ -z "$1" ]
then
    echo "첫번째 파라미터값 공백이거나 null 입니다!"
    exit 1
if

kill.sh $1
echo $HOME/properties/$1/
java -jar ../$1.jar --spring.config.location=$HOME/properties/$1/