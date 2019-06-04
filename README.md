## [Link al problema](https://github.com/TiendaNube/java-integration-engineer-test0)


# Solución

## Stack
 * kotlin 1.3
 * spring-boot 2
 * spring-data-jdbc 2
 * arrow-kt 0.9.0
 * kotlin-test 3
 * junit 5

## Buildear proyecto
```bash
	./gradlew build
```
## Correr test
```bash
	./gradlew test
```

## Consideraciones de diseño y técnicas

* Se utiliza spring-data-jdbc y no spring-data-jpa, esto es por cuestiones de performance.
* Las clases Sale y Merchant poseen una property de tipo string (product y address) esto es para simplificar el modelo, puede reeemplazarse por clases.
* arrow-kt se usa para hacer un manejo de errores más funcional, haciendo uso de tipos "monádicos" como Try, Either.