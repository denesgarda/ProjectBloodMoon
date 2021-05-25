#! /bin/bash

set +v
cd "${0%/*}"
java -jar Launcher.jar
if [ $? -eq 100 ]
then
	clear
	cd ProjectBloodMoon
	java -jar ProjectBloodMoon.jar
	read -p "Press [ENTER] to continue . . ."
else
	if [ $? -ne 0 ]
	then
		echo Components to run the game are missing. Please download them at https://drive.google.com/drive/folders/1_aDBmAlZtB544GnEiuPZ-ENGvlCl6Lm8?usp=sharing
		read -p "Press [ENTER] to continue . . ."
	else
		read -p "Press [ENTER] to continue . . ."
	fi
fi