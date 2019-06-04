package com.tiendanube.challenge.dtos

import com.tiendanube.challenge.model.Merchant
import com.tiendanube.challenge.model.Plan
import com.tiendanube.challenge.model.Sale
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MerchantDto (
  @field:NotNull
  val id: Long?,
  @field:NotNull
  @field:NotEmpty
  val name: String?,
  @field:NotNull
  @field:NotEmpty
  val email: String?,
  @field:NotNull
  @field:NotEmpty
  val phone: String?,
  @field:NotNull
  @field:NotEmpty
  val address: String,
  val plan: Plan?,
  val sales: ArrayList<Sale>?,
  val balance: Double?,
  val credit: Double?
) {
  fun asModel() = Merchant(
    id?:0,
    name?:"",
    LocalDate.now(),
    email?:"",
    phone?:"",
    address,
    plan,
    sales?: arrayListOf(),
    balance?: 0.0,
    credit?: 0.0
  )
}
