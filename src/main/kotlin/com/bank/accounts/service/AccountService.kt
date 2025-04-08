package com.bank.accounts.service

import com.bank.accounts.entity.Account
import com.bank.accounts.exception.ResourceNotFoundException
import com.bank.accounts.model.TransactionRequest
import com.bank.accounts.model.UserInfoResponse
import com.bank.accounts.repository.AccountRepository
import com.bank.accounts.util.createLogger
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AccountService(
  private val userService: UserService,
  private val transactionService: TransactionService,
  private val accountRepository: AccountRepository
) {
  private val logger = createLogger()

  @Transactional
  fun createAccount(customerId: String, initialCredit: Double): Account {
    userService.getUserInfo(customerId)
      ?: throw ResourceNotFoundException("User with provided customerId not found!")
    val account = Account(customerId = customerId, balance = initialCredit)
    accountRepository.save(account)
    if (initialCredit > 0) {
      logger.info("Initiating transaction for customerId [$customerId]")
      transactionService.createTransaction(TransactionRequest(customerId, initialCredit))
    }
    return account
  }

  fun getUserInformation(customerId: String): UserInfoResponse {
    val user =
      userService.getUserInfo(customerId)
        ?: throw ResourceNotFoundException("User with provided customerId not found!")
    val account =
      accountRepository.findByCustomerId(customerId)
        ?: throw ResourceNotFoundException("Account for provided customerId not found!")
    val transactions = transactionService.getTransactions(customerId)
    return UserInfoResponse(user.name, user.surname, account.balance, transactions)
  }
}
