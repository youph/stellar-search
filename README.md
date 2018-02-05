# Investigative Analytics - Search Module

## Build status

 |Branch|Status|Coverage|
 |:-----|:----:|:----:|
 |*master*|[![Build Status](https://travis-ci.org/data61/stellar-search.svg?branch=master)](https://travis-ci.org/data61/stellar-search)|[![Code Coverage](https://coveralls.io/repos/github/data61/stellar-search/badge.svg?branch=master)](https://coveralls.io/github/data61/stellar-search)|
 |*develop*|[![Build Status](https://travis-ci.org/data61/stellar-search.svg?branch=develop)](https://travis-ci.org/data61/stellar-search)|[![Code Coverage](https://coveralls.io/repos/github/data61/stellar-search/badge.svg?branch=develop)](https://coveralls.io/github/data61/stellar-search)|

## Setup

### Development Prerequisites (For MacOS)
 
 * [HomeBrew](https://brew.sh/) 
 
 * Oracle JDK 8+
   ```bash
   brew tap caskroom/versions
   brew cask install java8
   ```
   and add `export JAVA_HOME=$(/usr/libexec/java_home --version 1.8)` to your `~/.zshrc`.
   Or if you are ballsy, you can try java 9
   ```bash
   brew cask install java 
   ```
   and add instead `export JAVA_HOME=$(/usr/libexec/java_home --version 9)` to your `~/.zshrc`.
   
* Maven
  ```bash
  brew install maven
  ```
  and add `export M2_HOME=/usr/local/opt/maven/libexec` to your `~/.zshrc`
  
* [Docker Community Edition (CE)](https://www.docker.com/community-edition)
  ```bash
  brew cask install docker
  ```
  To complete the installation, open Docker in application or Launchpad and follow prompts.
  
* [Postman](https://www.getpostman.com/) (Optional) 
  ```bash
  brew cask install postman
  ```

### IDE setup

If you IDE is doing incremental compilation, you will need to ensure that `-parameters` compile
option is provided and that your Java byte code language level target is 8+. Need for 
https://github.com/FasterXML/jackson-modules-java8/tree/master/parameter-names.

## Running Docker stack
See `docker/README.md`
 
## Building 

Your docker daemon must be running since the build will spin up and tear down an elasticsearch
docker container for integration tests, and build a docker image of the application

To compile, run checkstyle, unit/integration tests, generate a spring executable jar, and build
a docker image `data61/stellar-search:<version>` of the application run
```bash
mvn clean verify
```

## Running
```bash
java -jar ./search/target/ia-search-<version>-exec.jar --spring.profiles.active=develop
```

Example to create a new IMDB Extended Property Graph (EPG) Index
```bash
BASE_PATH="file:$(pwd)/search/src/test/resources/au/com/d2dcrc/ia/search/epg" && \
curl -XPOST \
  -H "Content-Type: application/json" \
  -d '{
  "graphs": "'"${BASE_PATH}/imdb-graphs.json"'",
  "edges": "'"${BASE_PATH}/imdb-edges.json"'",
  "vertices": "'"${BASE_PATH}/imdb-vertices.json"'"
}' http://localhost:8080/api/v1.0/indexes/imdb
```

### Viewing API Documentation

[Swagger](https://swagger.io/) is used for our API tooling.

Visit [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs) for JSON formatted Swagger documentation.  

Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for the interactive Swagger UI

You may also import the API from Swagger into Postman

## Postman

Postman is optional - but is a nice tool.

In the top toolbar hit `Import -> Import from link` and enter `http://localhost:8080/v2/api-docs`.
A new Investigative Analytics API collection will be created with all documented APIs 

## Spring Profiles

**NOTE** Due to spring profile inheritance / including, a profile(s) MUST be specified for anything to work. Either

1. IntelliJ Spring Boot run/debug configuration - Active profiles setting  

2. As a JVM property
    ```
    java -Dspring.profiles.active={develop|test|staging|production} -jar ./search/target/ia-search-<version>.jar
    ```

3. As a program argument (e.g. for self executing jar - but can also be used with `java -jar ...`)
    ```
    ./search/target/ia-search-<version>-exec.jar --spring.profiles.active={develop|test|staging|production}
    ```

#### Application profiles

These are the supported (mutually exclusive) profiles we can run our applications in

* **develop** - Uses a local H2 database stored **on disk**. Changes are persisted and available between app restarts  
* **test** - Uses a local H2 database stored **in memory**. Changes are not persisted between app restarts  
* **staging** - Uses a local (docker) Postgres database. Allows multiple applications to share a database. 
  Note may not be required if this is just one app.
* **production** - Eventually may use a central Postgres database 

## Developer tools

Spring Boot automatically provides additional set of tools for development. Developer tools are
automatically disabled when running a fully packaged application. If your application is launched
using java -jar or if it’s started using a special classloader, then it is considered a
“production application”

A browser based H2 web console is available for development at 
[http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)

See [Spring Boot Devtools](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-devtools)
for more info on using devtools for automatic restart and live-reload.

## Git workflow
[Gitflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow) is used for
the branching model and the maven [jgitflow](https://bitbucket.org/atlassian/jgit-flow/wiki/Home)
plugin is used for release branch management, artifact deployment and tagging. 

Branches
* `master`: Latest stable and tagged release
* `develop`: Latest snapshot
* `feature/<JIRA ID LOWERCASE>-<OPTIONAL DESCRIPTION LOWERCASE>`

Note due to Windows/MacOS being a case-insensitive filesystem (but bitbucket/github not) please always
checkout and create branches (including JIRA IDs) in lowercase. Commit messages should still 
be prefixed with `JIRA-ID: ` in uppercase.

Feature branches should ideally be squash committed, then rebased onto develop.

## Codestyle

We use the [Google Java Code Style](https://google.github.io/styleguide/javaguide.html) with the 
following exceptions

* 4.2 Block Indentation: We use +4 spaces for block indentation instead of 2.
* 4.4 Column limit: We use a column limit of 120 characters instead of 100.
* 4.6.3 Horizontal alignment: Where google permits but discourages its use, we generally disallow

And with the following additional rules

* Where a variable can be `final` it should be.
* Any linewrapped
  - method declaration parameters, 
  - method call arguments, 
  - class `implements` lists,
  - method `throws` declaration and 
  - try with resources
  
  items start on a new line, indented 4 spaces from the current block and with the closing
  delimiter if any (`implements` and `throws` don't have a closing delimiter) on a new line at
  the current block's indentation level. It is advised, but not required, that any items list if that
  is new-lined is `chopped down` with each item on its own line.

  Examples
    ```java
    // WRONG - violates 4.6.3 Horizontal alignment rule
    public void method(final String param1,
                       final String param2,
                       final String param3) {
        // ...
    }
    
    // WRONG - 1st param not on new line, closing bracket not on new line
    public void method(final String param1, final String param2
        final String param3, final String param4) {
        // ...
    }
    
    // RIGHT
    public static void veryLongMethodName(
      final String param1, // chopped down items
      final String param2,
      final String param3,
      final String param4
    ) throws E1, E2, E3 { // permitted if fits on one line
      // ...
    } 
  
    // RIGHT
    public String method2(final String p1, final String p2) throws
      VeryLongException1,
      VeryLongException2,
      VeryLongException3 {
      // ...
    }
  
    // RIGHT
     veryLongObject.veryLongMethod(
       arg1, arg2, arg3 // permitted if fits on one line
     ).chainedMethod(
       longArgument1,
       longArgument2,
       longArgument3,
       longArgument4
     );
    ```

This code style in enforced (where it can) by the maven build by the checkstyle config in the 
`build-tools` module, which is based off
[http://checkstyle.sourceforge.net/google_style.html](http://checkstyle.sourceforge.net/google_style.html) 
checkstyle configuration.

## Resources

* [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Spring Framework Reference](https://docs.spring.io/spring/docs/current/spring-framework-reference/)
* [Spring Guides](https://spring.io/guides)
* [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)
* [Elasticsearch REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html)
