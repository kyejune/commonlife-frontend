#!/bin/bash
name=$1
if [ -z $name ]
then
  name="ykim"
fi

TOMCAT_HOME=/Users/ykim/dev_local/tomcat/apache-tomcat-7.0.81/

export MAVEN_OPTS=-Dspring.profiles.active=$name
echo "MAVEN_OPTS=${MAVEN_OPTS}"
echo "cleaning..."
mvn clean;
echo "building..."
mvn compile;
mvn test ${MAVEN_OPTS};
#mvn package -Dspring.profiles.active=$name;
