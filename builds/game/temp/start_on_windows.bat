@ECHO OFF
CALL java -jar ProjectBloodMoon.jar
IF "%errorlevel%" == "100" (
	CALL cls
	CALL update.bat
) ELSE (
	IF "%errorlevel%" NEQ "0" (
		echo Components to run the game are missing. Please download them at https://drive.google.com/drive/folders/1_aDBmAlZtB544GnEiuPZ-ENGvlCl6Lm8?usp=sharing
	)
	PAUSE
)