package com.tiendanube.challenge.exceptions

import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError

data class MerchantAlreadyExistException(override val message: String): RuntimeException(message)
data class NoResourceFoundException(override val message: String): RuntimeException(message)
data class BadRequestException(val bindingResult: BindingResult) : RuntimeException() {
  override val message: String
    get() {
      val sb = StringBuilder("Validation failed ")
      if (this.bindingResult.errorCount > 1) {
        sb.append(" with ").append(this.bindingResult.errorCount).append(" errors")
      }
      sb.append(": ")
      for (error in this.bindingResult.allErrors) {
        val fieldError = error as FieldError
        sb.append("[ field: ").append(fieldError.field).append(", constraint: ").append(fieldError.defaultMessage).append(" ] ")
      }
      return sb.toString()
    }
}