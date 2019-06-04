package com.tiendanube.challenge.model

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.time.LocalDate

/**
 * Created by tomReq on 6/3/19.
 */
class SaleSpec: BehaviorSpec({
  given("sale") {
    val today = LocalDate.now()
    val sale = Sale(1, "shirt", 200.toDouble(), today)
    `when`("ask for properties"){
      val product = sale.product
      val amount = sale.amount
      then("should be equals to sale") {
        product shouldBe "shirt"
        amount shouldBe 200.toDouble()
        today shouldBe sale.creationDate
      }
    }
  }
})