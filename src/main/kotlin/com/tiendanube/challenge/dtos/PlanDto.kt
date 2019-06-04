package com.tiendanube.challenge.dtos

import com.tiendanube.challenge.model.Plan
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * Created by tomReq on 6/3/19.
 */
data class PlanDto(
  @field:NotNull
  val id: Long?,
  @field:NotNull
  @field:NotEmpty
  val name: String?,
  @field:NotNull
  val fee: Double?
) {
  fun asModel() = Plan(id?:0, name?:"", fee?:0.0)
}