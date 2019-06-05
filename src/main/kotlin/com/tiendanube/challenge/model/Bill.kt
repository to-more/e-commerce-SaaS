package com.tiendanube.challenge.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.util.*

data class Bill(
  val code: UUID,
  @field:JsonProperty("creation_date") val creationDate: LocalDate,
  @field:JsonProperty("total_amount") val totalAmount: Double,
  @field:JsonProperty("total_fee") val totalFee: Double
)
