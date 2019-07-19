#!/bin/sh

clear;
echo "1. Generate password escaped application.properties_sample "
./src/main/resources/shell_scripts/escape_prop.sh
echo "  "

echo "2. clean up temporary directories"
#rm ./logs/romssynch*
#rm ./tmp_work/romssynch/*
echo "  "

echo "2. Configure Spring Boot runtime environments"
export JMX_OPTS="-Dcom.sun.management.jmxremote.port=3099 -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=127.0.0.1"
export SPRING_LOADED_OPTS=""
#export SPRING_LOADED_OPTS="-javaagent:/opt/dev/lib/spring-loaded/springloaded-1.2.7.RELEASE.jar -noverify"
export MAVEN_OPTS="-Xmx384m -XX:+UseG1GC ${JMX_OPTS} ${SPRING_LOADED_OPTS}"
echo "---------------------------------------------------------------------------------------------"
echo "mvn spring-boot:run -Drun.arguments=--spring.config.location=./dev_conf/application.properties"
echo "   - with MAVEN_OPTS : ${MAVEN_OPTS}							   "
echo "---------------------------------------------------------------------------------------------"
echo "  "
sleep 1

echo "3. Maven clean"
mvn clean;
echo "  "
sleep 1

echo "4. Starting Spring Boot application"
mvn spring-boot:run
# mvn spring-boot:run -Drun.arguments=--spring.config.location=./dev_conf/application.properties

