package com.tiendanube.challenge.dtos

import com.tiendanube.challenge.model.Sale
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * Created by tomReq on 6/3/19.
 */
data class SaleDto (
  @field:NotNull
  val id: Long?,
  @field:NotNull
  @field:NotEmpty
  val product: String?,
  @field:NotNull
  val amount: Double?
) {
  fun asModel() = Sale(id?:0, product?:"", amount?:0.0, LocalDate.now())
}