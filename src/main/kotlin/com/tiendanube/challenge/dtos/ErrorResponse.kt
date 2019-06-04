package com.tiendanube.challenge.dtos

import com.fasterxml.jackson.annotation.JsonProperty

class ErrorResponse(
  @field:JsonProperty("error_code")
  val code: Int,
  @field:JsonProperty("error_message")
  val message: String?
)