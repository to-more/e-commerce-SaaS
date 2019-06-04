package com.tiendanube.challenge.model

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

/**
 * Created by tomReq on 6/3/19.
 */
class PlanSpec: BehaviorSpec({
  given("a plan") {
    val fee = 2.toDouble()
    val name = "basic"
    `when`("it creates") {
      val plan = Plan(1, name, fee)
      then("should be equals") {
        plan.fee shouldBe fee
        plan.name shouldBe name
      }
    }
  }
})