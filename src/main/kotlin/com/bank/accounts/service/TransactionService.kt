package com.bank.accounts.service

import com.bank.accounts.model.TransactionRequest
import com.bank.accounts.model.TransactionResponse
import com.bank.accounts.util.createLogger
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TransactionService(private val restTemplate: RestTemplate) {

  companion object {
    private val logger = createLogger()
    private const val BASE_API_URL = "http://localhost:8091/v1/transactions"
  }

  fun createTransaction(transaction: TransactionRequest): TransactionResponse? {
    return try {
      val request = HttpEntity(transaction)
      val response: ResponseEntity<TransactionResponse> =
        restTemplate.postForEntity("$BASE_API_URL/create", request, TransactionResponse::class.java)
      response.body
    } catch (ex: Exception) {
      logger.error("Failed to create transaction for customerId=${transaction.customerId}")
      throw ex
    }
  }

  fun getTransactions(customerId: String): List<TransactionResponse> {
    return try {
      val url = "$BASE_API_URL/customerId/$customerId"
      val response: ResponseEntity<Array<TransactionResponse>> =
        restTemplate.getForEntity(url, Array<TransactionResponse>::class.java)

      response.body?.toList().orEmpty()
    } catch (ex: Exception) {
      logger.error("Failed to fetch transactions for customerId=$customerId")
      throw ex
    }
  }
}
