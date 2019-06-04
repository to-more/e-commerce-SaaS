package com.tiendanube.challenge.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

@Table("merchants")
data class Merchant(
  @Id val id: Long,
  val name: String,
  @Column("creation_date") val creationDate: LocalDate,
  val email: String,
  val phone: String,
  val address: String,
  @Column("plan_id") val plan: Plan?,
  val sales: ArrayList<Sale>?,
  val balance: Double,
  val credit: Double
)