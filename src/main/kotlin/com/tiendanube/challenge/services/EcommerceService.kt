package com.tiendanube.challenge.services

import arrow.core.Either
import arrow.core.Try
import arrow.core.extensions.`try`.monad.binding
import com.tiendanube.challenge.daos.MerchantDao
import com.tiendanube.challenge.dtos.MerchantDto
import com.tiendanube.challenge.exceptions.MerchantAlreadyExistException
import com.tiendanube.challenge.exceptions.NoResourceFoundException
import com.tiendanube.challenge.extensions.benchmark
import com.tiendanube.challenge.model.Merchant
import com.tiendanube.challenge.model.Plan
import com.tiendanube.challenge.model.Sale
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

/**
 * Created by tomReq on 6/3/19.
 */
@Service
class EcommerceService {

  @Autowired
  lateinit var merchantDao: MerchantDao

  companion object {
    @JvmStatic
    val logger = LoggerFactory.getLogger(EcommerceService::class.java)
  }

  fun saveMerchant(merchantDto: MerchantDto): Either<Throwable, Merchant> = logger.benchmark("Create merchant $merchantDto") {
    binding {
      val model = merchantDto.asModel()
      Try {
        merchantDao.create (
          model.id,
          model.name,
          model.creationDate,
          model.email,
          model.phone,
          model.address,
          model.balance,
          model.credit,
          model.plan?.id
        )
      }.bind()
      model
    }.toEither {
      when {
        it is DuplicateKeyException -> MerchantAlreadyExistException("There is already a merchant registered with the same email ${merchantDto.email} and phone ${merchantDto.phone}")
        else -> it
      }
    }
  }

  fun update(id: Long, merchantDto: MerchantDto): Either<Throwable, Merchant> = logger.benchmark("Create merchant $merchantDto") {
    findById(id).fold({
      Either.left(it)
    }, {
      Try {
        val model = merchantDto.asModel()
        merchantDao.update (
          model.id,
          model.name,
          model.creationDate,
          model.email,
          model.phone,
          model.address
        )
        model
      }.toEither {
        when {
          it is DuplicateKeyException -> MerchantAlreadyExistException("There is already a merchant registered with the same email ${merchantDto.email} and phone ${merchantDto.phone}")
          else -> it
        }
      }
    })
  }

  fun findById(
    merchantId: Long
  ): Either<Throwable, Merchant> = logger.benchmark("call merchant dao with $merchantId") {
    binding {
      val (merchant) = Try { merchantDao.getById(merchantId) }
      val (sales) = Try { merchantDao.getSales(merchantId) }
      merchant.copy(sales = ArrayList(sales))
    }.toEither {
      when {
        it is EmptyResultDataAccessException -> NoResourceFoundException(it.message?: "Resource not found")
        else -> it
      }
    }
  }

  fun delete(merchantId: Long): Either<Throwable, Long> = logger.benchmark("call merchant dao with $merchantId") {
    findById(merchantId).fold({
      Either.left(it)
    }, {
      Try {
        merchantDao.deleteSales(it.id)
        merchantDao.deleteMerchant(it.id)
      }.toEither()
    })
  }

  fun addSale(
    merchantId: Long,
    sale: Sale
  ): Either<Throwable, Merchant> = logger.benchmark("Register of sale $sale for merchant $merchantId") {

    findById(merchantId).fold({
      Either.left(it)
    }, {
      Try {
        merchantDao.saveSale(sale.id, sale.creationDate, sale.product, sale.amount, it.id)
        it.sales?.add(sale)
        it
      }.toEither()
    })
  }

  fun updatePlan(
    merchantId: Long,
    plan: Plan
  ): Either<Throwable, Merchant> = logger.benchmark("update plan $plan for merchant $merchantId") {
    findById(merchantId).fold({
      Either.left(it)
    }, {
      Try {
        merchantDao.updatePlan(it.id, plan.id)
        it.copy(plan = merchantDao.getPlan(plan.id))
      }.toEither()
    })
  }
}