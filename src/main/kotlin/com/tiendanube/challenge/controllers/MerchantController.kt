package com.tiendanube.challenge.controllers

import arrow.core.Either
import com.tiendanube.challenge.dtos.MerchantDto
import com.tiendanube.challenge.dtos.PlanDto
import com.tiendanube.challenge.dtos.SaleDto
import com.tiendanube.challenge.exceptions.BadRequestException
import com.tiendanube.challenge.extensions.benchmark
import com.tiendanube.challenge.services.EcommerceService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

/**
 * Created by tomReq on 6/3/19.
 */
@RestController
@RequestMapping("/merchants")
class MerchantController {

  @Autowired
  lateinit var ecommerceService: EcommerceService
  @Autowired
  lateinit var errorHandler: ErrorHandler

  companion object {
    @JvmStatic
    val logger = LoggerFactory.getLogger(MerchantController::class.java)
  }

  @GetMapping("/{id}")
  fun getMerchant(
    @PathVariable("id") merchantId: Long
  ) = logger.benchmark("Call to e-ccomerce service") {
    ecommerceService.findById(merchantId).fold({ throwable ->
      errorHandler.handle(throwable)
    }, {
      it
    })
  }

  @DeleteMapping("/{id}")
  fun deleteMerchant(
    @PathVariable("id") merchantId: Long
  ) = logger.benchmark("Call delete to e-ccomerce service") {
    ecommerceService.delete(merchantId).fold({ throwable ->
      errorHandler.handle(throwable)
    }, {
      ResponseEntity.ok().build()
    })
  }

  @PostMapping
  fun create(
    uriComponentsBuilder: UriComponentsBuilder,
    @RequestBody @Valid merchantDto: MerchantDto,
    result: BindingResult
  ) = logger.benchmark("Call e-commerce service for creation $merchantDto") {
    Either.cond(!result.hasErrors(), {
      ecommerceService.saveMerchant(merchantDto).fold({
        errorHandler.handle(it)
      },{
        ResponseEntity.created(
          uriComponentsBuilder.path("/merchants/{id}")
            .buildAndExpand(merchantDto.id)
            .toUri()
        ).build()
      })
    },{
      BadRequestException(result)
    }).fold({ throwable ->
      errorHandler.handle(throwable)
    }, {
      it
    })
  }

  @PutMapping("/{id}")
  fun update(
    @PathVariable("id") id: Long,
    @RequestBody @Valid merchantDto: MerchantDto,
    result: BindingResult
  ) = logger.benchmark("Call e-commerce service for update $merchantDto") {
    Either.cond(!result.hasErrors(), {
      ecommerceService.update(id, merchantDto).fold({
        errorHandler.handle(it)
      },{
        ResponseEntity.ok(it)
      })
    },{
      BadRequestException(result)
    }).fold({ throwable ->
      errorHandler.handle(throwable)
    }, {
      it
    })
  }

  @PutMapping("/{id}/plan")
  fun updatePlan(
    @PathVariable("id") id: Long,
    @RequestBody @Valid plan: PlanDto,
    result: BindingResult
  ) = logger.benchmark("Updating plan of merchant $id") {
    Either.cond(!result.hasErrors(), {
      ecommerceService.updatePlan(id, plan.asModel()).fold({
        errorHandler.handle(it)
      },{
        ResponseEntity.ok(it)
      })
    }, {
      BadRequestException(result)
    }).fold({ throwable ->
      errorHandler.handle(throwable)
    }, {
      it
    })
  }

  @PutMapping("/{id}/sale")
  fun updateSale(
    @PathVariable("id") id: Long,
    @RequestBody @Valid sale: SaleDto,
    result: BindingResult
  ) = logger.benchmark("Add sale $sale merchant $id") {
    Either.cond(!result.hasErrors(), {
      ecommerceService.addSale(id, sale.asModel()).fold({
        errorHandler.handle(it)
      }, {
        ResponseEntity.ok(it)
      })
    }, {
      BadRequestException(result)
    }).fold({ throwable ->
      errorHandler.handle(throwable)
    }, {
      it
    })
  }
}