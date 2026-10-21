@echo off
cd /d "%~dp0"
java -cp "build\classes;sqlitejdbc-v056.jar;rs2xml.jar;jcalendar.jar;JTattoo-1.6.11.jar" Authentication
pause
