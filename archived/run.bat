:: @echo off
:: RUN.BAT - Runs the LostCities applet via the Sun appletviewer
if "%JAVA_HOME%" == "" set JAVA_HOME=C:\JBuilder6\jdk1.3.1\bin
echo Using JAVA_HOME = %JAVA_HOME%
%JAVA_HOME%\appletviewer app_view.html
pause