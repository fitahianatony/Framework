cd framework
javac -d bin src/*.java
cd bin
jar -cvf ../../fwk.jar *
cd ../../
copy C:\'Program Files'\'Apache Software Foundation'\'Tomcat 10.0'\webapps\Framework\fwk.jar C:\'Program Files'\'Apache Software Foundation'\'Tomcat 10.0'\lib\fwk.jar
copy C:\'Program Files'\'Apache Software Foundation'\'Tomcat 10.0'\webapps\Framework\fwk.jar C:\'Program Files'\'Apache Software Foundation'\'Tomcat 10.0'\webapps\Framework\test-framework\WEB-INF\lib\fwk.jar
cd test-framework/WEB-INF/classes
javac -d . *.java
cd ../../../