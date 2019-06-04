package com.tiendanube.challenge.daos

import com.tiendanube.challenge.model.Merchant
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDate

/**
 * Created by tomReq on 6/3/19.
 */
interface MerchantDao: CrudRepository<Merchant, Long> {

  @Modifying
  @Query("""
    INSERT /* class: MerchantDao, query: insert-merchant */ INTO merchants (id, name, creation_date, email, phone, address, balance, credit, plan_id)
    VALUES (:id, :name, :creation_date, :email, :phone, :address, :balance, :credit, :plan_id)
    """
  )
  fun create(
    @Param("id") id: Long,
    @Param("name") name: String,
    @Param("creation_date") creationDate: LocalDate,
    @Param("email") email: String,
    @Param("phone") phone: String,
    @Param("address") address: String,
    @Param("balance") balance: Double,
    @Param("credit") credit: Double,
    @Param("plan_id") plan: Long?
  ): Long

  @Modifying
  @Query("""
    UPDATE /* class: MerchantDao, query: insert-merchant */ merchants
    set name=:name,
      creation_date=:creation_date,
      email=:email,
      phone=:phone,
      address=:address
      WHERE id=:id
    """
  )
  fun update (
    @Param("id") id: Long,
    @Param("name") name: String,
    @Param("creation_date") creationDate: LocalDate,
    @Param("email") email: String,
    @Param("phone") phone: String,
    @Param("address") address: String
  ): Long

  @Query("""
    SELECT merchants.id      AS id,
     merchants.NAME          AS NAME,
     merchants.creation_date AS creation_date,
     merchants.email         AS email,
     merchants.phone         AS phone,
     merchants.address       AS address,
     merchants.balance       AS balance,
     merchants.credit        AS credit,
     PLAN.id                 AS plan_id,
     PLAN.NAME               AS plan_name,
     PLAN.fee                AS plan_fee,
     sale.id                 AS sales_id,
     sale.product            AS sales_product,
     sale.amount             AS sales_amount,
     sale.creation_date      AS sales_creation_date
    FROM merchants
     LEFT JOIN plans AS PLAN ON PLAN.id = merchants.plan_id
     LEFT JOIN sales AS sale ON sale.merchant_id = merchants.id
    WHERE  merchants.id = :id
    """)
  fun getById(@Param("id") id: Long): Merchant

  @Modifying
  @Query("""
    update merchants set plan_id = :idPlan where id = :id
  """)
  fun updatePlan(@Param("id") id: Long, @Param("idPlan") idPlan: Long): Long

  @Modifying
  @Query("""
    insert into sales(id, creation_date, product, amount, merchant_id)
    values(:idSale, :creationDate, :product, :amount, :idMerchant)
  """)
  fun saveSale(
    @Param("idSale") idSale: Long,
    @Param("creationDate") creationDate: LocalDate,
    @Param("product") product: String,
    @Param("amount") amount: Double,
    @Param("idMerchant") merchant: Long
  ): Long

  @Modifying
  @Query("""
    DELETE FROM sales WHERE merchant_id = :idMerchant
  """)
  fun deleteSales(@Param("idMerchant") idMerchant: Long): Long

  @Modifying
  @Query("""
    DELETE FROM merchants WHERE id = :idMerchant
  """)
  fun deleteMerchant(@Param("idMerchant") idMerchant: Long): Long
}
