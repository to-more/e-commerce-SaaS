package com.tiendanube.challenge.model

import java.time.LocalDate
import java.util.*

data class Bill(val code: UUID, val creationDate: LocalDate, val totalAmount: Double)
