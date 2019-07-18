#!/usr/bin/env bash
java -jar -Dspring.config.location=./dev_conf/application.properties -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/opt/dev/gc_log_test.out ./target/sb2-template.jar
