@echo off & setlocal enabledelayedexpansion
set WEBLOGIC_CLIENT_VERSION=10.3.6
set LIB_JARS=""
cd ..\lib
for %%i in (*) do set LIB_JARS=!LIB_JARS!;..\lib\%%i
cd ..\bin

if ""%1"" == ""debug"" goto debug
if ""%1"" == ""jmx"" goto jmx

java -Xms512m -Xmx1024m -XX:MaxPermSize=256M -classpath ..\conf;%LIB_JARS%;%WEBLOGIC_CLIENT_JAR_HOME%\wlfullclient-%WEBLOGIC_CLIENT_VERSION%.jar com.alibaba.dubbo.container.Main
goto end

:debug
java -Xms512m -Xmx1024m -XX:MaxPermSize=256M -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -classpath ..\conf;%LIB_JARS% com.alibaba.dubbo.container.Main
goto end

:jmx
java -Xms512m -Xmx1024m -XX:MaxPermSize=256M -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -classpath ..\conf;%LIB_JARS% com.alibaba.dubbo.container.Main

:end
pause