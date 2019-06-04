package com.tiendanube.challenge.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("plans")
data class Plan (
  @Id val id: Long,
  val name: String,
  val fee: Double
)