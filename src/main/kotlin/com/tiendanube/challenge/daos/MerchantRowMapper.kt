package com.tiendanube.challenge.daos

import arrow.core.Success
import arrow.core.Try
import com.tiendanube.challenge.model.Merchant
import com.tiendanube.challenge.model.Plan
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * Created by tomReq on 6/4/19.
 */
class MerchantRowMapper: RowMapper<Merchant> {
  override fun mapRow(
    rs: ResultSet,
    rowNum: Int
  ): Merchant {
    val plan = Try {
      Plan (
        rs.getLong("plan_id"),
        rs.getString("plan_name"),
        rs.getDouble("plan_fee")
      )
    }
    return Merchant(
      rs.getLong("id"),
      rs.getString("name"),
      rs.getDate("creation_date").toLocalDate(),
      rs.getString("email"),
      rs.getString("phone"),
      rs.getString("address"),
      when (plan) {
        is Success -> plan.value
        else -> null
      },
      arrayListOf(),
      0.0,
      0.0
    )
  }
}