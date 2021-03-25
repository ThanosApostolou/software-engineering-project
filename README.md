# HMMYs Got Talent

Αθανασόπουλος Μιχαήλ Παναγιώτης 03113909  
Αποστόλου Θάνος 03112910  
Βασιλόπουλος Νικόλαος 03113080  
Διακόπουλος Ορέστης 03113101  
Λαζάρου Σταύρος 03112642  
Τσολίσου Δάφνη 03109752 

## Build & Run
Clone this repository

- With Intellij Idea
  1. Import build.gradle
     >File-> New-> Project from Existing Sources (check Use auto-import)
  2. Build-> Build Project
  3. Run-> Run 'Application'
- With terminal
  1. cd softEngProject2018
  2. ./gradlew build
  3. ./gradlew bootRun

Use https:localhost:8765 for webapp and https:localhost:8765/observatory/api/ for restful api.

## Connect Database
  1. You need mysql server running (or mariadb)
  2. Create the needed user, give him the needed privileges, create the database tables and add the initial data by running at the mysql shell (usually as root with "`mysql -u root -p`"):
  ```
  CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
  GRANT ALL PRIVILEGES ON softeng2018.* TO 'user'@'localhost';
  source PATH/TO/softEngProject2018/src/main/resources/sql/schema.sql;
  source PATH/TO/softEngProject2018/src/main/resources/sql/data.sql;
  ```

## Deploy
1. Build Project
2. Create jar with `./gradlew bootJar`
3. Distribute `build/libs/softEngProject2018-VERSION.jar`
4. Run it with `java -jar softEngProject2018-VERSION.jar`


## Usefull infromation
#### Procedure for creating key for https:
```
run `keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650`
set password 'hmmysgottalent'
put keystore.p12 at src/main/resources/keystore
enable ssl at src/main/resources/application.properties
```
#### Procedure for connecting mysql:
