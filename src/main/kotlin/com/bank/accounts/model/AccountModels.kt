package com.bank.accounts.model

import java.time.LocalDateTime

// Models

data class AccountRequest(val customerId: String, val initialCredit: Double)

data class AccountResponse(val customerId: String, val balance: Double)

data class UserInfoResponse(
  val name: String,
  val surname: String,
  val balance: Double,
  val transactions: List<TransactionResponse>
)

data class TransactionRequest(val customerId: String, val amount: Double)

data class TransactionResponse(
  val timestamp: LocalDateTime,
  val transactionId: String,
  val customerId: String,
  val amount: Double,
)
