# Web Prices Observatory
Web application for buying products and searching stores.

### Technologies used:
  - Backend: Java, Spring Boot/MVC/Data JPA, Thymeleaf, MySQL.
  - Frontend: HTML, CSS, Javascript, jQuery, Bootstrap.

## Build & Run
Clone this repository

- With Intellij Idea
  1. Import build.gradle
     >File-> New-> Project from Existing Sources (check Use auto-import)
  2. Build-> Build Project
  3. Run-> Run 'Application'
- With terminal
  1. cd web-prices-observatory
  2. ./gradlew build
  3. ./gradlew bootRun

Use https:localhost:8765 for webapp and https:localhost:8765/observatory/api/ for restful api.

## Connect Database
  1. You need mysql server running (or mariadb)
  2. Create the needed user, give him the needed privileges, create the database tables and add the initial data by running at the mysql shell (usually as root with "`mysql -u root -p`"):
  ```
  CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
  GRANT ALL PRIVILEGES ON softeng2018.* TO 'user'@'localhost';
  source PATH/TO/web-prices-observatory/src/main/resources/sql/schema.sql;
  source PATH/TO/web-prices-observatory/src/main/resources/sql/data.sql;
  ```

## Deploy
1. Build Project
2. Create jar with `./gradlew bootJar`
3. Distribute `build/libs/web-prices-observatory-VERSION.jar`
4. Run it with `java -jar web-prices-observatory-VERSION.jar`


## Usefull infromation
#### Procedure for creating key for https:
```
run `keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650`
set password 'hmmysgottalent'
put keystore.p12 at src/main/resources/keystore
enable ssl at src/main/resources/application.properties
```
#### Procedure for connecting mysql:
