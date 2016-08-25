
Technologies used:

The primary technology used in this case study with following components:

Spring4.0 - provides a container to run the application
Spring Web Mvc- Spring framework to implement applications REST behaviors
JdbcTemplete and Mongo Template- Java to Database connectivity based on the Data Source.
spring-restdocs -Automated documentation implemented to keep code and services API in sync.
JUnit - Implements automated integration tests
Jacoco- Code Coverage Analysis.
Log4j- Logging the data.
Maven- Automatic Build Script.
Databases- Postgres(Relational) and MongoDB(NoSQL)
GitHub- Repository
RestClient-Postman/SOAPUI
Server-Tomcat 7.0


Code overview:

The source is broken into 2 parts

main -contains the application implementation.
test  -contains unit tests of the application.

The  implementation is broken into 6 distinct packages:

1. com.target.request  - Defines the input objects.
2. com.target.response - Defines the response objects.
3. com.target.dao - Implements the layer that will manage db access and queries using JdbcTemplate.
4. com.target.service - the application layer that encapsulates business logic
5. com.target.rest - the application later that implement the service endpoint interfaces
6. com.target.exception - custom exception handling.


datasource.properties - This is the data source configuration file located in src/main/resources.
applicationContext.xml-This is the xml where all the spring configuration is defined. Location: /spring/applicationContext.xml
dataConfig.xml-Data source definition for Postgres and MongoDB. Location: /spring/dataConfig.xml

dataconfig.xml is imported in applicationContext.xml and applicationContext is defined in web.xml
pom.xml- Master build file for Maven.


Steps to run application:

Assumptions:

a) JDK 1.7 or later is installed on your machine
b) Postgres and MongoDB instances are accessible
c) Access the internet to download Maven dependencies
d) Postman Client(Or any other rest client)/SOAPUI is plugged in.

Steps
1. Unzip the provided zip distribution in a directory of your choice say <HOME>/Download it from github.
2. cd <HOME>/target
3. Import the project to your IDE and download the maven dependencies(mvn clean build). This downloads all your dependencies and run the unit test cases.You can find it in the target folder.
4. Create the database tables based on dbschema_Postgres.txt, dbschema_MongoDB.txt and define your properties in dataconfig.xml.
6. Deploy the war file on tomcat server.
7. If you are using current application configuration you should be able to access application URL at:

Get Product: http://localhost:8082/Retail/ProductTest/Currency/1001
Get Product from Multiple data source: http://localhost:8082/Retail/ProductTest/SearchFromMultiple?id=1001
Get External Product: http://localhost:8082/Retail/ProductTest/ExternalProduct?id=13860428
Update Product to NoSQL: http://localhost:8081/Retail/ProductTest/1002
Update Product into Postgres: http://localhost:8082/Retail/ProductTest/Update/1001


