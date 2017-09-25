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


## Quick Run
- Run the service with default profile (The service will run on port `8080`).

```
$ java -jar target/academy-classroom-service.jar
```
- Check the status of the service at [http://localhost:8080/health](http://localhost:8080/health). The status should be `UP`.
- Open a [sample classroom](http://localhost:8080/classroom/100)



## Run the service locally
If you want to run the service and all its downstream locally:

- Run the dependent services using `local` profile:
  - `academy-service`: See instructions [here](https://github.com/htchepannou/academy-service#run-the-server-locally)
  - `academy-login-webui`: See instructions [here](https://github.com/htchepannou/academy-login-webui#run-the-service-locally)
- Run the service using `local` profile (The service will run on port `vvvdvvdfcticiivgverkvktueheivekvttjnjfflikiv
28080`):
```
$ java -Dspring.profiles.active=local -jar target/academy-classroom-webui.jar
```
- Check the status of the service at [http://localhost:28080/health](http://localhost:28080/health). The status should be `UP`.
- Open a [sample classroom](http://localhost:28080/classroom/100)




## License
This project is open source sofware under the [MIT License](https://opensource.org/licenses/MIT)
