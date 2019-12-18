FROM openjdk:8-jdk-alpine
EXPOSE 8080
# ADD command does not extract zip file
ADD /target/sb2-template.jar sb2-template.jar
# use 'docker run -v /tmp/host_filesystem:/opt/docker_filesystem ... ', VOLUME option creates file under docker image directory
# VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# careful to set '-Xms256m' & '-Xmx1024m' as separate paramter
ENTRYPOINT ["java", "-Xms256m", "-Xmx1024m", "-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1199 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.rmi.port=1188","-jar", "sb2-template.jar"]


# Build/tag image & Run container
#    $ docker image build -t sb2template .
#    $ docker images
#    $ docker tag 8978a536aade devcat24/sb2template:1.0.1
#    $ docker container run -p 7200:8200 -p 7100:8100 --name sb2 -d devcat24/sb2template:1.0.1
#    $ docker logs -f sb2
#    $ docker exec -it sb2 /bin/sh

