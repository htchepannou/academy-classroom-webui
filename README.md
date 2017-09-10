Master: [![Build Status](https://travis-ci.org/htchepannou/academy-classroom-webui.svg?branch=master)](https://travis-ci.org/htchepannou/academy-classroom-webui)
[![Code Coverage](https://img.shields.io/codecov/c/github/htchepannou/academy-classroom-webui/master.svg)](https://codecov.io/github/htchepannou/academy-classroom-webui?branch=master)
[![JDK](https://img.shields.io/badge/jdk-1.8-brightgreen.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)

# Academy Classroom WebUI
Web UI for classroom where students attend their courses online.


## Requirements
- Java 1.8
- Maven


## Installation
Clone the code repository locally and build it.
```
$ git clone git@github.com:htchepannou/academy-classroom-webui.git
$ cd academy-classroom-webui
$ mvn compile
$ mvn clean install
```

This will generate the service binary ``target/academy-classroom-webui.jar``


## Run
```
$ java -jar target/academy-classroom-service.jar
```
Navigate to a [sample classroom](http://localhost:8080/classroom/100)


## Links
- Local Environment
    - [Service Health](http://localhost:8080/health) 

- Integration Environment
    - [Sample classroom](https://io-tchepannou-a-classroom-web.herokuapp.com/classroom/100)
    - [Service Health](https://io-tchepannou-a-classroom-web.herokuapp.com/health) 


## License
This project is open source sofware under the [MIT License](https://opensource.org/licenses/MIT)
