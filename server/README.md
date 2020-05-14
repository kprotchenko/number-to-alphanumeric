# phone-parser server
This is a backend module for number-to-alphanumeric web application
To run the server, cd into the server folder and run:
```
mvn spring-boot:run
```
or you can instead build and run a docker image by executing these commands in the terminal:
```
mvn clean install
docker build -t server .
docker run -it -p 8080:8080 --name server server
```

