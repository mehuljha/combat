# combat
Download Grails version 4.0.10 and setup: 
https://grails.org/download.html

If on Windows, just download the zip, extract it and set environment variables:
GRAILS_HOME : Installed directory
Update Path add grails bin

Optional:
Grails app can work with in memory H2 Database, or you can configure any database of your choice. Current configuration of project points to localhost MySQL server.
You can download MySQL server from https://dev.mysql.com/downloads/installer/
If using MySQL database, you can configure the settings in the application.yml file for database name and credentials.

Open a command prompt and run comman grails run-app. 
This command should start the application on default port 8080.
