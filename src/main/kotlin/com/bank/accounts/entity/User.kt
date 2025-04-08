package com.bank.accounts.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
  @JsonProperty("customer_id") val customerId: String = "CUST0001",
  val name: String = "John",
  val surname: String = "Smith"
)
