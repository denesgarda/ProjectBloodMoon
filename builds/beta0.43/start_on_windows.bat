@ECHO OFF
CALL java -jar Launcher.jar
IF "%errorlevel%" == "3000" (
	CALL cls
	CALL java -jar ProjectBloodMoon.jar
) ELSE (
	IF "%errorlevel%" NEQ "0" (
		echo Components to run the game are missing. Please download them at https://drive.google.com/drive/folders/1_aDBmAlZtB544GnEiuPZ-ENGvlCl6Lm8?usp=sharing
	)
)
PAUSE