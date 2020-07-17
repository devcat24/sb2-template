#!/bin/sh

clear;
echo "1. Generate password escaped application.properties_sample "
echo "   -> currently disabled "
# ./src/main/resources/shell_scripts/escape_prop.sh
echo "  "

#echo "2. clean up temporary directories"
#rm ./logs/romssynch*
#rm ./tmp_work/romssynch/*
#echo "  "

echo "2. Configure Spring Boot runtime environments"
# export JMX_OPTS="-Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=127.0.0.1"
export MAVEN_OPTS="-Xmx384m -XX:+UseG1GC ${JMX_OPTS}"
echo "---------------------------------------------------------------------------------------------"
echo "mvn -Dspring.config.location=./dev_conf/application.properties package"
echo "---------------------------------------------------------------------------------------------"
sleep 1
echo "  "

echo "3. Maven clean"
mvn clean;
echo "  "
sleep 1

echo "4. Starting Spring Boot application"
#mvn -Dspring.config.location=./dev_conf/application.properties clean package install -U
# mvn -Dspring.config.location=./dev_conf/application.properties package
mvn clean package
