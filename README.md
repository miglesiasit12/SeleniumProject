# Michael-Iglesias Project

Test project for TopTal interview process. UI, API and Performance testing

## Installation

Project requires gradle and java 11 to be installed

https://gradle.org/install/
https://mail.codejava.net/java-se/download-and-install-java-11-openjdk-and-oracle-jdk

## Usage

in the project root using, command-line or an IDE run

```
gradle test
```

Multiple environment variables can be passed to modify the way the tests run EX:

```
gradle test -DbrowserType="FIREFOX" -DisRemoteDriverRun="false"
```

###browserType
CHROME and FIREFOX are supported types
Default value: CHROME

###isRemoteDriverRun 
Determines whether the test uses remoteWebdriver or a local browser
Default value: true

###remoteWebDriverHost 
The host that the remorteWebDriver connects to
Default value: http://localhost:4444/wd/hub

###mapApiBaseUrl 
URL that the api exists on
Default value: http://localhost

###mapApiBasePath
path that the api exists on within the url
Default value: /api/v1

###mapApiPort
port that the api uses
Default value: 8000

##Docker
A docker compose file exists to deploy the API and Selenoid which the remoteWebdriver connects to

```
docker-compose -f ./Docker/docker-compose.yml up -d 
```