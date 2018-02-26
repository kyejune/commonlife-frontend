TOMCAT_HOME=/Users/ykim/dev_local/tomcat/apache-tomcat-7.0.81/
MAVEN_OPTS="-Dspring.profiles.active=ykim -DskipTests"


echo "MAVEN_OPTS=${MAVEN_OPTS}"

#echo "cleaning..."
#mvn clean;

echo "building..."
mvn compile;

#mvn test ${MAVEN_OPTS};
mvn package ${MAVEN_OPTS};
