
	P H O N E B O O K

	Application example for es17


Added mysql database persistence through the FenixFramework using a DML specification.
Tests refactored to execute within database transactions.

Before executing the application, ensure that the database is created with the correct user and password, as identified in the file:

src/main/resources/fenix-framework-jvstm-ojb.properties

For example, user='phone' passwd='book' dbase='phonebook':

	$ mysql -p -u root
	Enter password: rootroot

	mysql> GRANT ALL PRIVILEGES ON *.* TO 'phone'@'localhost' IDENTIFIED BY 'book' WITH GRANT OPTION;

	mysql> CREATE DATABASE phonebook;

	mysql> \q

	$ mvn clean package exec:java

