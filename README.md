# Michael-Iglesias Project

Test project for TopTal interview process. UI, API and Performance testing

## Installation

Project requires gradle, java 11, and apache jmeter to be installed

https://gradle.org/install/

https://mail.codejava.net/java-se/download-and-install-java-11-openjdk-and-oracle-jdk

## Usage

in the project root using, command-line or an IDE run

```
gradle test
```

Multiple environment variables can be passed to modify the way the tests run

```
gradle test -DbrowserType="FIREFOX" -DisRemoteDriverRun="false"
```

### browserType
CHROME and FIREFOX are supported types
Default value: CHROME

### isRemoteDriverRun 
Determines whether the test uses remoteWebdriver or a local browser
Default value: true

### remoteWebDriverHost 
The host that the remorteWebDriver connects to
Default value: http://localhost:4444/wd/hub

### mapApiBaseUrl 
URL that the api exists on
Default value: http://localhost

### mapApiBasePath
path that the api exists on within the url
Default value: /api/v1

### mapApiPort
port that the api uses
Default value: 8000

## Docker
A docker compose file exists to deploy the API and Selenoid (the remoteWebdriver connects to this service)

```
docker-compose -f ./Docker/docker-compose.yml up -d 
```

## Apache Jmeter (Performance Testing)

To start jmeter go to apache-jmeter-5.4.1/bin/ApacheJMeter after downloading it.

The JMX exists in the jmeter folder from the project root.

Under the user defined variables, the path can be set where the graphs and results will be stored after the run

https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-5.4.zip