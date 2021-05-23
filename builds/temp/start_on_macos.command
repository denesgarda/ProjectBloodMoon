#! /bin/bash

set +v

cd "${0%/*}"
java -jar Launcher.jar

if [ $? -eq 184 ]
then
    clear
    java -jar ProjectBloodMoon.jar
else
    echo Components to run the game are missing. Please download them at https://drive.google.com/drive/folders/1_aDBmAlZtB544GnEiuPZ-ENGvlCl6Lm8?usp=sharing
fi
