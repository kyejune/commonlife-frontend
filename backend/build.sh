TOMCAT_HOME=/Users/ykim/dev_local/tomcat/apache-tomcat-7.0.81/


echo "Buidling..."
mvn install

echo "Shutting tomcat down...."
$TOMCAT_HOME/bin/shutdown.sh

echo "Starting tomcat up...."
$TOMCAT_HOME/bin/startup.sh


