@echo off
title Apex Bank - Modern Banking Management System
echo =======================================================
echo   Starting Apex Bank Web Application...
echo   Directly connected to: bank.sqlite
echo =======================================================
cd /d "%~dp0\web-banking-app"

start "" http://localhost:3000
node server.js
pause
