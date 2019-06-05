package com.tiendanube.challenge.model

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.time.LocalDate
import java.util.*

/**
 * Created by tomReq on 6/3/19.
 */
class BillSpec: BehaviorSpec({
  given("a bill properties") {
    val date = LocalDate.of(2019, 6, 2)
    val totalAmount = 100.0
    val totalFee = 100.0
    val code = UUID.randomUUID()
    `when`("it creates a bill") {
      val bill = Bill(code, date, totalAmount, totalFee)
      then("should be equals") {
        bill.code shouldBe code
        bill.creationDate shouldBe date
        bill.totalAmount shouldBe totalAmount
      }
    }
  }
})