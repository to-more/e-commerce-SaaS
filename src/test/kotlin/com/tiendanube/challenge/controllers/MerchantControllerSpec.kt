package com.tiendanube.challenge.controllers

import arrow.core.Either
import com.tiendanube.challenge.dtos.MerchantDto
import com.tiendanube.challenge.exceptions.MerchantAlreadyExistException
import com.tiendanube.challenge.exceptions.NoResourceFoundException
import com.tiendanube.challenge.model.Merchant
import com.tiendanube.challenge.model.Plan
import com.tiendanube.challenge.model.Sale
import com.tiendanube.challenge.services.EcommerceService
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.specs.BehaviorSpec
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.springframework.dao.QueryTimeoutException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import java.time.LocalDate

/**
 * Created by tomReq on 6/3/19.
 */
class MerchantControllerSpec: BehaviorSpec({
  given("merchant controller and mocks") {

    val ecommerceService = mock(EcommerceService::class.java)
    val errorHandler = ErrorHandler()

    val merchantController = MerchantController()
    merchantController.ecommerceService = ecommerceService
    merchantController.errorHandler = errorHandler
    val plan = Plan(1, "Basic", 2.0)
    val merchant = Merchant(1, "test", LocalDate.now(), "email", "123", "Address", plan, arrayListOf(), 0.0, 0.0)
    val mockMvc = standaloneSetup(merchantController).build()
    val merchantDto = MerchantDto(1, "Test", "mail@mail.io", "1234566", "Address", null, null, null, null)
    `when`("save of merchant") {

      then("create without errors") {
        doReturn(Either.right(1L)).`when`(ecommerceService).saveMerchant(merchantDto)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(
            post("/merchants").contentType(MediaType.APPLICATION_JSON).content("""
             {
              "id": 1,
              "name": "Test",
              "email": "mail@mail.io",
              "phone": "1234566",
              "address": "Address"
             }
            """)).andReturn().response
          response.status shouldBe HttpStatus.CREATED.value()
        }
      }
      then("create with errors") {
        doReturn(Either.left(NoResourceFoundException("Not found"))).`when`(ecommerceService).saveMerchant(merchantDto)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(
            post("/merchants").contentType(MediaType.APPLICATION_JSON).content("""
             {
              "id": 1,
              "name": "Test",
              "email": "mail@mail.io",
              "phone": "1234566",
              "address": "Address"
             }
            """)).andReturn().response
          response.status shouldBe HttpStatus.NOT_FOUND.value()
        }
      }
      then("create with duplication errors") {
        doReturn(Either.left(MerchantAlreadyExistException("Duplicate merchant"))).`when`(ecommerceService).saveMerchant(merchantDto)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(
            post("/merchants").contentType(MediaType.APPLICATION_JSON).content("""
             {
              "id": 1,
              "name": "Test",
              "email": "mail@mail.io",
              "phone": "1234566",
              "address": "Address"
             }
            """)).andReturn().response
          response.status shouldBe HttpStatus.CONFLICT.value()
        }
      }
      then("create with bad request errors") {
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(
            post("/merchants").contentType(MediaType.APPLICATION_JSON).content("""
             {
              "id": 1,
              "name": null,
              "email": "mail@mail.io",
              "phone": "1234566",
              "address": "Address"
             }
            """)).andReturn().response
          response.status shouldBe HttpStatus.BAD_REQUEST.value()
        }
      }
    }
    `when`("Get by id"){
      then("get with no errors") {
        doReturn(Either.right(merchant)).`when`(ecommerceService).findById(1L)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(get("/merchants/1")).andReturn().response
          response.status shouldBe HttpStatus.OK.value()
        }
      }
      then("get with not found errors") {
        doReturn(Either.left(NoResourceFoundException("Not found"))).`when`(ecommerceService).findById(1L)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(get("/merchants/1")).andReturn().response
          response.status shouldBe HttpStatus.NOT_FOUND.value()
        }
      }
      then("get with errors") {
        doReturn(Either.left(QueryTimeoutException("Exception"))).`when`(ecommerceService).findById(1L)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(get("/merchants/1")).andReturn().response
          response.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
        }
      }
    }
    `when`("Delete by id"){
      then("delete with no errors") {
        doReturn(Either.right(1L)).`when`(ecommerceService).delete(1L)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(delete("/merchants/1")).andReturn().response
          response.status shouldBe HttpStatus.OK.value()
        }
      }
      then("delete with not found no errors") {
        doReturn(Either.left(NoResourceFoundException("not found"))).`when`(ecommerceService).delete(1L)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(delete("/merchants/1")).andReturn().response
          response.status shouldBe HttpStatus.NOT_FOUND.value()
        }
      }
    }
    `when`("update merchant"){
      then("update with no errors") {
        doReturn(Either.right(merchant)).`when`(ecommerceService).update(1L, merchantDto)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "id": 1,
              "name": "Test",
              "email": "mail@mail.io",
              "phone": "1234566",
              "address": "Address"
             }
          """)).andReturn().response
          response.status shouldBe HttpStatus.OK.value()
        }
      }
      then("update with not found errors") {
        doReturn(Either.left(NoResourceFoundException("Not found"))).`when`(ecommerceService).update(1L, merchantDto)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "id": 1,
              "name": "Test",
              "email": "mail@mail.io",
              "phone": "1234566",
              "address": "Address"
             }
          """)).andReturn().response
          response.status shouldBe HttpStatus.NOT_FOUND.value()
        }
      }
      then("update with bad request errors") {
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "id": 1,
              "name": null,
              "email": null,
              "phone": "1234566",
              "address": "Address"
             }
          """)).andReturn().response
          response.status shouldBe HttpStatus.BAD_REQUEST.value()
        }
      }
    }
    `when`("update plan of merchant"){
      val planUpdate = Plan(1, "Medium", 0.5)
      then("update plan") {
        doReturn(Either.right(merchant)).`when`(ecommerceService).updatePlan(1L, planUpdate)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/plan")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "name": "Medium",
              "fee": 0.5
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.OK.value()
        }
      }
      then("update with not found error") {
        doReturn(Either.left(NoResourceFoundException("not found"))).`when`(ecommerceService).updatePlan(1L, planUpdate)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/plan")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "name": "Medium",
              "fee": 0.5
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.NOT_FOUND.value()
        }
      }
      then("update with bad request error") {
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/plan")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "name": "",
              "fee": 0.5
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.BAD_REQUEST.value()
        }
      }
      then("update plan - server errors") {
        doReturn(Either.left(QueryTimeoutException("timeout"))).`when`(ecommerceService).updatePlan(1L, planUpdate)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/plan")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "name": "Medium",
              "fee": 0.5
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
        }
      }
    }
    `when`("register sale of merchant"){
      val sale = Sale(1, "sale", 200.0, LocalDate.now())
      then("add sale plan") {
        doReturn(Either.right(merchant)).`when`(ecommerceService).addSale(1L, sale)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/sale")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "product": "sale",
              "amount": 200.0
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.OK.value()
        }
      }
      then("add sale plan with not found errors") {
        doReturn(Either.left(NoResourceFoundException(""))).`when`(ecommerceService).addSale(1L, sale)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/sale")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "product": "sale",
              "amount": 200.0
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.NOT_FOUND.value()
        }
      }
      then("add sale plan with internal bad request errors") {
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/sale")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "product": "sale",
              "amount": null
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.BAD_REQUEST.value()
        }
      }
      then("add sale plan with internal server errors") {
        doReturn(Either.left(QueryTimeoutException(""))).`when`(ecommerceService).addSale(1L, sale)
        shouldNotThrow<Throwable> {
          val response = mockMvc.perform(put("/merchants/1/sale")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
              "id": 1,
              "product": "sale",
              "amount": 200.0
              }
              """)).andReturn().response
          response.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
        }
      }
    }
  }
})