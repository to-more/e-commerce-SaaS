# [Link al problema](https://github.com/TiendaNube/java-integration-engineer-test0)


# Solution
* [Stack](#stack)
* [Build](#gradle-build)
* [Docker build/run](#docker-build-and-run)
* [Test](#run-test)
* [Tech and design concerns](#tech-and-design-concerns)

## REST Resources
* [Merchant](/doc/merchant.md)

## Stack
 * kotlin 1.3
 * spring-boot 2
 * spring-data-jdbc
 * arrow-kt 0.9.0
 * kotlin-test
 * junit 5
 * test-containers
 * rest-assured
 * PostgreSQL

## Gradle build

To build the project with gradle in the root directory it should be executed the gradle build task

```bash

 cd $root_dir

./gradlew build

```
## Run test

To run test separately to the build run the gradle tasks test  

```bash
./gradlew test
```

## Docker build and run

To build the project with docker it should be executed de script Build.sh. The script receives an optional parameter the root directory of the project otherwise the value is $PWD

```bash
sh Build.sh $root_dir
``` 
#### The previous script does:
 * build an image of postgreSQL with the schema created
 * build an image with spring boot jar, the merchant REST API
 
#### To run the database and the spring boot application execute

```bash
sh Run.sh
``` 

After the script execution the database in the 5432 port and the spring boot application in the 8080 port are running   
 
## Tech and design concerns

* Spring-data-jdbc is used instead of spring-data-jpa, this is for performance reasons.
* The Sale and Merchant classes have a field of type string (product and address respectively) this is to simplified the model, it can be replaced by classes.
* arrow-kt is used to implement functional error handling, making use of "monadic" types like Try, Either.
