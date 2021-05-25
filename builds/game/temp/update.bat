@ECHO OFF
CALL java -jar Updater.jar
IF "%errorlevel%" == "110" (
	CALL cls
	CALL java -jar ProjectBloodMoon.jar
) ELSE (
	PAUSE
)