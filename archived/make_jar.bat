@echo off
:: MAKE_JAR.BAT - Creates the .JAR file that contains the "Lost Cities" Java classes
:: "C:\Program Files\JBuilder4\jdk1.3\bin\jar" /?
:: Arguments:
:: c = create archive
:: f = to a file
:: v = verbose
if "%JAVA_HOME%" == "" set JAVA_HOME=C:\JBuilder6\jdk1.3.1\bin
echo Using JAVA_HOME = %JAVA_HOME%
if exist (LostCities.jar) del LostCities.jar
%JAVA_HOME%\jar cfv LostCities.jar lostCities
pause