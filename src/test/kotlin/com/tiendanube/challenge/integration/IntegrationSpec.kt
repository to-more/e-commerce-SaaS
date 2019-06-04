package com.tiendanube.challenge.integration

import com.tiendanube.challenge.ChallengeApplication
import com.tiendanube.challenge.model.Plan
import io.restassured.RestAssured
import io.restassured.RestAssured.*
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.testcontainers.containers.PostgreSQLContainer
import javax.annotation.PostConstruct


class KPostgreSQLContainer(imageName: String): PostgreSQLContainer<KPostgreSQLContainer>(imageName)

/**
 * Created by tomReq on 6/4/19.
 */
@SpringBootTest(classes = [ ChallengeApplication::class ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup(value = [
  Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:create-schema.sql"]),
  Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = ["classpath:drop-schema.sql"])]
)
class IntegrationSpec {

  @Autowired
  lateinit var namedParametersJdbcTemplate: NamedParameterJdbcTemplate

  @LocalServerPort
  var serverPort: Int = 0

  @PostConstruct
  fun postConstruct(){
    RestAssured.port = serverPort
  }

  companion object {

    val postgresContainer = KPostgreSQLContainer("postgres")
      .withDatabaseName("postgres-test")
      .withUsername("postgres-test")
      .withPassword("secretpassword")
      .withExposedPorts(54322)

    @BeforeAll
    @JvmStatic
    fun setUp(){
      postgresContainer.start()
      System.setProperty("datasource.postgre.jdbcUrl", postgresContainer.jdbcUrl)
      System.setProperty("datasource.postgre.username", postgresContainer.username)
      System.setProperty("datasource.postgre.password", postgresContainer.password)
    }

    @AfterAll
    @JvmStatic
    fun afterAll() {
      postgresContainer.stop()
    }
  }

  @Test
  fun testPlansInitialization() {
    val plans = namedParametersJdbcTemplate.query(
      """
        SELECT * FROM plans
      """
    ) { rs, _ ->
      Plan(
        rs.getLong(1),
        rs.getString(2),
        rs.getDouble(3)
      )
    }

    assert(3 == plans.size)
  }

  @Test
  fun testGetMerchant(){
    given()
      .contentType("application/json")
      .body("""
       {
        "id": 1,
        "name": "Test",
        "email": "mail@mail.io",
        "phone": "1234566",
        "address": "Address"
       }
      """)
      .`when`()
        .post("/merchants")
      .then()
        .statusCode(HttpStatus.CREATED.value())
    get("/merchants/1")
      .then()
        .body("name", equalTo("Test"))
  }

  @Test
  fun testDeleteMerchant(){
    given()
      .contentType("application/json")
      .body("""
       {
        "id": 1,
        "name": "Test",
        "email": "mail@mail.io",
        "phone": "1234566",
        "address": "Address"
       }
      """)
      .`when`()
        .post("/merchants")
      .then()
        .statusCode(HttpStatus.CREATED.value())
    get("/merchants/1")
      .then()
        .body("name", equalTo("Test"))
    delete("/merchants/1")
      .then()
        .statusCode(HttpStatus.OK.value())
    get("/merchants/1")
      .then()
        .statusCode(HttpStatus.NOT_FOUND.value())
  }

  @Test
  fun testUpdateMerchant() {
    given()
      .contentType("application/json")
      .body("""
       {
        "id": 1,
        "name": "Test",
        "email": "mail@mail.io",
        "phone": "1234566",
        "address": "Address"
       }
      """)
      .`when`()
      .post("/merchants")
      .then()
      .statusCode(HttpStatus.CREATED.value())
    get("/merchants/1")
      .then()
      .body("name", equalTo("Test"))
    given()
      .contentType("application/json")
      .body("""
        {
        "id": 1,
        "name": "Tom",
        "email": "mail@mail.io",
        "phone": "1234566",
        "address": "Address"
       }
      """)
      .`when`()
      .put("/merchants/1")
      .then()
      .statusCode(HttpStatus.OK.value())
    get("/merchants/1")
      .then()
      .body("name", equalTo("Tom"))
  }

  @Test
  fun testUpdateSale(){
    given()
      .contentType("application/json")
      .body("""
       {
        "id": 1,
        "name": "Test",
        "email": "mail@mail.io",
        "phone": "1234566",
        "address": "Address"
       }
      """)
      .`when`()
        .post("/merchants")
      .then()
        .statusCode(HttpStatus.CREATED.value())
    get("/merchants/1")
      .then()
        .body("name", equalTo("Test"))
    given()
      .contentType("application/json")
      .body("""
        {
        "id": 1,
        "product": "sale-test",
        "amount": 200.23
       }
      """)
      .`when`()
        .put("/merchants/1/sale")
      .then()
        .statusCode(HttpStatus.OK.value())
    get("/merchants/1")
      .then()
        .body("sales[0].product", equalTo("sale-test"))
  }
}