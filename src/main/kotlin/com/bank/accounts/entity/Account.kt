package com.bank.accounts.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "accounts")
data class Account(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
  @JsonProperty("customer_id") val customerId: String = "CUST00001",
  var balance: Double = 0.0
)
