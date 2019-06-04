package com.tiendanube.challenge

import com.tiendanube.challenge.extensions.percentage
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

/**
 * Created by tomReq on 6/3/19.
 */
class ExtensionsSpec: StringSpec({

  "get percentage of a given number" {
    50 percentage 10 shouldBe 5.toDouble()
  }
})
