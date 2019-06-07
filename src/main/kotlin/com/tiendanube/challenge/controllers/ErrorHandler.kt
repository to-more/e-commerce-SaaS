package com.tiendanube.challenge.controllers

import com.tiendanube.challenge.dtos.ErrorResponse
import com.tiendanube.challenge.exceptions.BadRequestException
import com.tiendanube.challenge.exceptions.ConflictingDataException
import com.tiendanube.challenge.exceptions.NoResourceFoundException
import com.tiendanube.challenge.extensions.logError
import com.tiendanube.challenge.extensions.logWarn
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class ErrorHandler {

  companion object {
    @JvmStatic
    private val logger = LoggerFactory.getLogger(ErrorHandler::class.java)
    @JvmStatic
    val GENERIC_ERROR_CODE = 9999
  }

  fun handle(e: Throwable) = when (e) {
    is NoResourceFoundException -> {
      logWarn(logger, e)
      ResponseEntity.notFound().build()
    }
    is ConflictingDataException -> {
      logWarn(logger, e)
      ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(GENERIC_ERROR_CODE, e.message))
    }
    is BadRequestException -> {
      logWarn(logger, e)
      ResponseEntity.badRequest().body(ErrorResponse(GENERIC_ERROR_CODE, e.message))
    }
    else -> {
      logError(logger, e)
      ResponseEntity(ErrorResponse(GENERIC_ERROR_CODE, e.message), HttpStatus.INTERNAL_SERVER_ERROR)
    }
  }

  @ExceptionHandler(value = [Throwable::class])
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleServerError(e: Throwable): ResponseEntity<ErrorResponse> {
    logError(logger, e)
    return ResponseEntity(ErrorResponse(GENERIC_ERROR_CODE, e.message), HttpStatus.INTERNAL_SERVER_ERROR)
  }

}