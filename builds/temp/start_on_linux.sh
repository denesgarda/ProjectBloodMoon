#! /bin/bash

cd "${0%/*}"
java -jar Launcher.jar
if [[ $? -eq 3000 ]]
then
    java -jar ProjectBloodMoon.jar
elif [[ $?  -ne 0]]
then
    echo Components to run the game are missing. Please download them at https://drive.google.com/drive/folders/1_aDBmAlZtB544GnEiuPZ-ENGvlCl6Lm8?usp=sharing

sleep 5
