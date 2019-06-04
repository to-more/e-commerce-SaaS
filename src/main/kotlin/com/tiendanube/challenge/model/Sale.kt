package com.tiendanube.challenge.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("sales")
data class Sale(
  @Id val id: Long,
  val product: String,
  val amount: Double,
  @Column("creation_date") val creationDate: LocalDate
)
