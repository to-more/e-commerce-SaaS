package com.tiendanube.challenge.dtos

import com.tiendanube.challenge.model.Plan
import javax.validation.constraints.NotNull

/**
 * Created by tomReq on 6/3/19.
 */
data class PlanDto(
  @field:NotNull
  val id: Long?
) {
  fun asModel() = Plan(id?:0, "", 0.0)
}