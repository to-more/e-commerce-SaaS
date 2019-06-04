package com.tiendanube.challenge.model

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import java.time.LocalDate

/**
 * Created by tomReq on 6/3/19.
 */
class MerchantSpec: BehaviorSpec({
  given("a merchant properties") {
    val name = "Tom"
    val creationDate = LocalDate.now()
    val email = "tom@tom.io"
    val phone = "12345678"
    val address = "my address"
    val plan = Plan(1, "", 1.toDouble())
    val sales = arrayListOf (Sale(1, "shirt", 200.toDouble(), LocalDate.now()))
    val balance = 0.0
    `when`("creates a merchant") {
      val merchant = Merchant(1, name, creationDate, email, phone, address, plan, sales, balance, 0.0)
      then("it should be equals to properties"){
        merchant.name shouldBe name
        merchant.creationDate shouldBe creationDate
        merchant.email shouldBe email
        merchant.phone shouldBe phone
        merchant.address shouldBe address
        merchant.plan shouldBe plan
        merchant.sales shouldBe sales
        merchant.balance shouldBe balance
        merchant.credit shouldBe 0.0
      }
    }
  }
})