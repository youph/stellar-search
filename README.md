# Investigative Analytics - Search Module

## Build status

 |Branch|Status|
 |:-----|:----:|
 |*master*|[![Build Status](https://travis-ci.org/data61/stellar-search.svg?branch=master)](https://travis-ci.org/data61/stellar-search)|
 |*develop*|[![Build Status](https://travis-ci.org/data61/stellar-search.svg?branch=develop)](https://travis-ci.org/data61/stellar-search)|

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

To compile, run checkstyle, unit/integration tests and generate a spring executable jar, run
```bash
mvn clean verify
```

## Running
```bash
./search/target/ia-search-<version>-exec.jar --spring.profiles.active=develop
```

Authenticate as the following test user to obtain a [JSON Web Token](https://jwt.io/introduction/) or JWT
```bash
curl -XPOST -H "Content-Type: application/json" -d '{
  "username": "test-user",
  "password": "only-for-development"
}' http://localhost:8080/api/v1/auth
```

The response JSON body will have the token in the `jwt` field. JWT is valid for 30 days by default
and persists between app restarts 

Include the JWT in the Authorization header for all subsequent requests. E.g
```bash
curl -XPOST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer xxxxx.yyyyy.zzzzz" \
  -d '{
  "name": "New task",
  "description": "Some description",
  "dateTime": "2015-01-03T00:00:00+10:30"
}' http://localhost:8080/api/v1/example/task/
```

See Postman section on how to do this automatically

### Viewing API Documentation

[Swagger](https://swagger.io/) is used for our API tooling.

Visit [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs) for JSON formatted Swagger documentation.  

Visit [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for the interactive Swagger UI

You may also import the API from Swagger into Postman

## Postman

Postman is optional - but is a nice tool.

In the top toolbar hit `Import -> Import from link` and enter `http://localhost:8080/v2/api-docs`.
A new Investigative Analytics API collection will be created with all documented APIs 

To have the Authorization header and bearer token implicitly added to every request,
edit the collection, then hit `Authorization`. Select `Bearer` and enter a JWT generated by
posting a username and password to `http://localhost:8080/api/v1/auth`. You may wish create an 
environment (e.g. named `develop`) and store the token in a variable named `jwt` instead. You
can refer to the token as `{{jwt}}` inside the collections's Authorization config.
 
**NOTE** Do _not_ surround the variable with whitespace (e.g. `{{ jwt }}`) as is the convention
for Jinja2/Ansible/Angular or else Postman will complain that the variable is not defined.

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

The `default` is automatically enabled and will depending on the application will include any necessary
additive profiles such as

* `secrets` - for storing sensitive information with restricted read access. Having secrets in a separate config files 
allows for having the 'locked-down' file permissions readable only by the service user, whilst still allowing
public configuration world-readable that are used across all environmental profiles.

### There are so many property files now! Where do I add new properties?

#### Basic property files

These property files are never referenced as a profile directly when running the application. They are simply included 
by other profiles in order to have sensible defaults in the same place, and allow composition depending on the mode the 
application is being run in
 
**application-db-common.yml** - properties that are truly common to all JPA database providers  
**application-h2.yml** - common properties specific to the H2 database  
**application-postgres.yml** - common properties specific to postgres  

#### Application specific properties

Any app specific property should go into the respective `application-{develop|test|staging|production}.yml` file.

# Git workflow
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

# Codestyle

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

# Resources

* [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [Spring Framework Reference](https://docs.spring.io/spring/docs/current/spring-framework-reference/)
* [Spring Guides](https://spring.io/guides)
* [Elasticsearch](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)
* [Elasticsearch REST Client](https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/index.html)
