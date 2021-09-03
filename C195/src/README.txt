-----

	Program and student information

Title - C195

Purpose - A GUI scheduling application built in Java. This application will allow users to login and connect to a MySQL database to create, read, update, and delete appointment, contact, country, customer, division, and user data. There are also various reports that can be ran and read.

Author - Kyle McIntyre

Contact - kmcin43@wgu.edu

Student Application Version - QAM1

Date - 9/2/2021

-----

	Technology Used

IDE - IntelliJ IDEA 2021.2.1 (Community Edition)

JDK - jdk-11.0.11

JavaFX - javafx-sdk-11.0.2

MySQL Connector driver - mysql-connector-java:8.0.22

-----

	How to run the program

You will need the above IDE, JDK, JavaFX and MySQL Connector driver to run this program. 

Unzip the file and load the project into IntelliJ.

Set the above javafx-sdk, and MySQL Connector driver under File > Project Structure > Libraries > +

You must then go to Run > Edit Configurations and select the above JDK by clicking the +. Then click on More Options > Add VM Options and then paste 
--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics 
into VM Options.

Next go to File > Settings > Appearance & Behavior > Path Variables. Click the + and add Name: PATH_TO_FX
and Value you will select where you have your javafx-sdk-11.0.2 saved. Click Apply and Ok

You can not build and run the application for the first time by Right clicking on Main > Main.java and select Run 'Main'

The default logins are
test - test
admin - admin

-----

	Third report

The third report that I have added displays all of the contact names and their associated emails.