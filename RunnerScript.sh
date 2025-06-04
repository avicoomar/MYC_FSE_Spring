mvn clean package;

#Setting variables (change accordingly if working on different system):
tomcatHome="$HOME/Downloads/apache-tomcat-10.1.31";
todayDate=$(timedatectl | grep -Po "\d+-\d+-\d+(?= \d+:\d+:\d+.IST)");
localhostLogFile=$(ls ~/Downloads/apache-tomcat-10.1.31/logs/ | grep "localhost\.$todayDate\.log");

#Clear logs:
echo "" > $tomcatHome/logs/$localhostLogFile;
echo "" > $tomcatHome/logs/catalina.out;

cp ./target/MYC_FSE_Spring-1.war $tomcatHome/webapps/; #Deploy webapp

#Stop and start tomcat:
echo "SHUTDOWN" | nc localhost 8005; #Shutdowm Tomcat if already running
while nc -z localhost 8080; do
	#Enter loop if exit code of "nc -z localhost 8080" is true/0 which means tomcat is still running
	echo "Waiting for Tomcat to shutdown";
	sleep 1;
done
bash $tomcatHome/bin/startup.sh; #Start Tomcat server

#sleep 15; #wait for tomcat(and your webapp) to start properly before loading the logs -- otherwise it doesn't show the logs nicely
tail -n 100 -f $tomcatHome/logs/"$localhostLogFile" $tomcatHome/logs/catalina.out;
