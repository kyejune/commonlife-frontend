#!/bin/bash
name=$1
if [ -z $name ]
then
  name="ykim"
fi

TOMCAT_HOME=~/dev_local/commonlife-tomcat/
MAVEN_OPTS="-Dspring.profiles.active=$name -DskipTests"

export JAVA_OPTS=-Dspring.profiles.active=ykim

echo "MAVEN_OPTS=${MAVEN_OPTS}"
echo "JAVA_OPTS=${JAVA_OPTS}"

echo "stopping tomcat..."
$TOMCAT_HOME/bin/shutdown.sh

echo "cleaning..."
mvn clean;

echo "building..."
mvn compile;

#mvn test ${MAVEN_OPTS};
mvn package ${MAVEN_OPTS};

echo "starting tomcat..."
$TOMCAT_HOME/bin/startup.sh

