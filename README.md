# [Link al problema](https://github.com/TiendaNube/java-integration-engineer-test0)


# Solution
* [Stack](#stack)
* [Build](#gradle-build)
* [Docker build](#docker-build)
* [Test](#run-test)
* [Consideraciones técnicas](#consideraciones-de-diseño-y-técnicas)

## Resources
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

To build the project with gradle in the root directory it should execute the build task

```bash

 cd $root_dir

./gradlew build

```
## Run test

To run test separately to the build run the gradle tasks test  

```bash
./gradlew test
```

## Docker build

To build the project with docker it should execute de script Build.sh receives an optional parameter the root director otherwise the value is $PWD

```bash
sh Build.sh $root_dir
``` 

## Tech and design concerns

* Spring-data-jdbc is used instead of spring-data-jpa, this is for performance reasons.
* The Sale and Merchant classes have a field of type string (product and address respectively) this is to simplified the model, it can be replaced by classes.
* arrow-kt is used to implement functional error handling, making use of "monadic" types like Try, Either.
