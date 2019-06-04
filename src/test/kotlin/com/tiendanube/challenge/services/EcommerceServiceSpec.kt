package com.tiendanube.challenge.services

import arrow.core.Either
import com.tiendanube.challenge.daos.MerchantDao
import com.tiendanube.challenge.dtos.MerchantDto
import com.tiendanube.challenge.exceptions.MerchantAlreadyExistException
import com.tiendanube.challenge.exceptions.NoResourceFoundException
import com.tiendanube.challenge.model.Merchant
import com.tiendanube.challenge.model.Plan
import com.tiendanube.challenge.model.Sale
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.specs.BehaviorSpec
import org.mockito.Mockito.*
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.dao.QueryTimeoutException
import java.time.LocalDate

/**
 * Created by tomReq on 6/3/19.
 */
class EcommerceServiceSpec: BehaviorSpec({

  given("e-commerce service and mocks"){

    val plan = Plan(1, "Basic", 2.0)
    val merchantDao = mock(MerchantDao::class.java)
    val merchant = Merchant(1, "test", LocalDate.now(), "email", "123", "Address", plan, arrayListOf(), 0.0, 0.0)
    val ecommerceService = EcommerceService()
    ecommerceService.merchantDao = merchantDao

    `when`("save of merchant") {
      val merchantDto = MerchantDto(1, "Test", "mail@mail.io", "1234566", "Address", plan, arrayListOf(), 10.0, 20.0)

      then("create without errors") {
        doReturn(1L).`when`(merchantDao).create(1, "Test", LocalDate.now(), "mail@mail.io", "1234566", "Address", 10.0, 20.0, plan.id)
        ecommerceService.saveMerchant(merchantDto).isRight() shouldBe true
      }

      then("create with duplicate key errors") {
        doThrow(DuplicateKeyException::class.java)
          .`when`(merchantDao).create(1, "Test", LocalDate.now(), "mail@mail.io", "1234566", "Address", 10.0, 20.0, plan.id)

        shouldNotThrow<Throwable> {
          val response = ecommerceService.saveMerchant(merchantDto)
          ((response as Either.Left<*>).a is MerchantAlreadyExistException) shouldBe true
        }
      }

      then("create with errors") {
        doThrow(QueryTimeoutException::class.java)
          .`when`(merchantDao).create(1, "Test", LocalDate.now(), "mail@mail.io", "1234566", "Address", 10.0, 20.0, plan.id)
        shouldNotThrow<Throwable> {
          val response = ecommerceService.saveMerchant(merchantDto)
          ((response as Either.Left<*>).a is QueryTimeoutException) shouldBe true
        }
      }
    }

    `when`("update of merchant") {
      val merchantDto = MerchantDto(1, "Test", "mail@mail.io", "1234566", "Address", plan, arrayListOf(), 10.0, 20.0)

      then("update without errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doReturn(1L).`when`(merchantDao).update(1, "Test", LocalDate.now(), "mail@mail.io", "1234566", "Address")
        ecommerceService.update(1L, merchantDto).isRight() shouldBe true
      }

      then("update with duplicate key errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doThrow(DuplicateKeyException::class.java)
          .`when`(merchantDao).update(1, "Test", LocalDate.now(), "mail@mail.io", "1234566", "Address")

        shouldNotThrow<Throwable> {
          val response = ecommerceService.update(1L, merchantDto)
          ((response as Either.Left<*>).a is MerchantAlreadyExistException) shouldBe true
        }
      }

      then("update with not found errors") {
        doThrow(EmptyResultDataAccessException::class.java)
          .`when`(merchantDao).getById(1)

        shouldNotThrow<Throwable> {
          val response = ecommerceService.update(1L, merchantDto)
          ((response as Either.Left<*>).a is NoResourceFoundException) shouldBe true
        }
      }

      then("update with errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doThrow(QueryTimeoutException::class.java)
          .`when`(merchantDao).update(1, "Test", LocalDate.now(), "mail@mail.io", "1234566", "Address")
        shouldNotThrow<Throwable> {
          val response = ecommerceService.update(1L, merchantDto)
          ((response as Either.Left<*>).a is QueryTimeoutException) shouldBe true
        }
      }
    }

    `when`("get of merchant") {

      then("return with no errors") {
        `when`(merchantDao.getById(1))
          .thenReturn(merchant)
        ecommerceService.findById(1L).isRight() shouldBe true
      }

      then("not found merchant") {
        doThrow(EmptyResultDataAccessException::class.java).`when`(merchantDao).getById(1)
        shouldNotThrow<Throwable> {
          val response = ecommerceService.findById(1L)
          ((response as Either.Left<*>).a is NoResourceFoundException) shouldBe true
        }
      }

      then("query exception in get of merchant") {
        doThrow(QueryTimeoutException::class.java).`when`(merchantDao).getById(1)
        shouldNotThrow<Throwable> {
          ecommerceService.findById(1L).isLeft() shouldBe true
        }
      }
    }

    `when`("delete a merchant") {

      then("delete with no errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doNothing().`when`(merchantDao).deleteById(1)
        ecommerceService.delete(1L).isRight() shouldBe true
      }

      then("not found merchant when try to delete") {
        doThrow(EmptyResultDataAccessException::class.java).`when`(merchantDao).getById(1)
        doNothing().`when`(merchantDao).deleteById(1)
        shouldNotThrow<Throwable> {
          val response = ecommerceService.delete(1L)
          ((response as Either.Left<*>).a is NoResourceFoundException) shouldBe true
        }
      }

      then("delete with errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doThrow(QueryTimeoutException::class.java).`when`(merchantDao).deleteById(1)
        shouldNotThrow<Throwable> {
          ecommerceService.delete(1L).isLeft() shouldBe true
        }
      }
    }

    `when`("add sale to a merchant") {
      val sale = Sale(1, "sale", 200.0, LocalDate.now())

      then("sale added with no errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doReturn(1L).`when`(merchantDao).saveSale(sale.id, sale.creationDate, sale.product, sale.amount, merchant.id)
        ecommerceService.addSale(1L, sale).isRight() shouldBe true
      }

      then("not found merchant when try to add a sale") {
        doThrow(EmptyResultDataAccessException::class.java).`when`(merchantDao).getById(1)
        shouldNotThrow<Throwable> {
          val response = ecommerceService.addSale(1L, sale)
          ((response as Either.Left<*>).a is NoResourceFoundException) shouldBe true
        }
      }

      then("add sale with errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doThrow(QueryTimeoutException::class.java)
          .`when`(merchantDao).saveSale(sale.id, sale.creationDate, sale.product, sale.amount, merchant.id)
        shouldNotThrow<Throwable> {
          ecommerceService.addSale(1L, sale).isLeft() shouldBe true
        }
      }
    }

    `when`("update plan of a merchant") {
      
      then("sale added with no errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doReturn(1L).`when`(merchantDao).updatePlan(merchant.id, plan.id)
        shouldNotThrow<Throwable> {
          ecommerceService.updatePlan(1L, plan).isRight() shouldBe true
        }
      }

      then("not found merchant when try to add a sale") {
        doThrow(EmptyResultDataAccessException::class.java).`when`(merchantDao).getById(1)
        shouldNotThrow<Throwable> {
          val response = ecommerceService.updatePlan(1L, plan)
          ((response as Either.Left<*>).a is NoResourceFoundException) shouldBe true
        }
      }

      then("add sale with errors") {
        doReturn(merchant).`when`(merchantDao).getById(1)
        doThrow(QueryTimeoutException::class.java)
          .`when`(merchantDao).updatePlan(merchant.id, plan.id)
        shouldNotThrow<Throwable> {
          ecommerceService.updatePlan(1L, plan).isLeft() shouldBe true
        }
      }
    }
  }
})