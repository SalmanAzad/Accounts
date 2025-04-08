package com.bank.accounts.service

import com.bank.accounts.entity.Account
import com.bank.accounts.entity.User
import com.bank.accounts.exception.ResourceNotFoundException
import com.bank.accounts.model.TransactionRequest
import com.bank.accounts.model.TransactionResponse
import com.bank.accounts.repository.AccountRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

class AccountServiceTest {

  private val userService: UserService = mock()
  private val transactionService: TransactionService = mock()
  private val accountRepository: AccountRepository = mock()
  private val accountService = AccountService(userService, transactionService, accountRepository)

  @Test
  fun `createAccount should create account and send transaction if initialCredit is greater than zero`() {
    // Arrange
    val customerId = "123"
    val initialCredit = 100.0
    val user = User(123, "John", "Doe")
    val account = Account(customerId = customerId, balance = initialCredit)

    `when`(userService.getUserInfo(customerId)).thenReturn(user)
    `when`(accountRepository.save(any())).thenReturn(account)

    // Act
    val result = accountService.createAccount(customerId, initialCredit)

    // Assert
    assertEquals(customerId, result.customerId)
    assertEquals(initialCredit, result.balance)
    verify(transactionService).createTransaction(TransactionRequest(customerId, initialCredit))
  }

  @Test
  fun `createAccount should throw exception if user not found`() {
    // Arrange
    val customerId = "999"
    val initialCredit = 50.0
    `when`(userService.getUserInfo(customerId)).thenReturn(null)

    // Act & Assert
    val exception =
      assertThrows<ResourceNotFoundException> {
        accountService.createAccount(customerId, initialCredit)
      }

    assertTrue(exception.message!!.contains("User with provided customerId not found!"))
  }

  @Test
  fun `getUserInformation should return user info with account and transactions`() {
    // Arrange
    val timestamp1 = LocalDateTime.now().plus(10, ChronoUnit.MINUTES)
    val timestamp2 = LocalDateTime.now().plus(15, ChronoUnit.MINUTES)
    val transaction1 = UUID.randomUUID().toString()
    val transaction2 = UUID.randomUUID().toString()
    val customerId = "321"
    val user = User(321, "CUST0001", "Alice", "Smith")
    val account = Account(12, "321", balance = 150.0)
    val transactions =
      listOf(
        TransactionResponse(timestamp1, transaction1, "", 50.0),
        TransactionResponse(timestamp2, transaction2, "", 100.0)
      )

    `when`(userService.getUserInfo(customerId)).thenReturn(user)
    `when`(accountRepository.findByCustomerId(customerId)).thenReturn(account)
    `when`(transactionService.getTransactions(customerId)).thenReturn(transactions)

    // Act
    val response = accountService.getUserInformation(customerId)

    // Assert
    assertEquals("Alice", response.name)
    assertEquals("Smith", response.surname)
    assertEquals(150.0, response.balance)
    assertEquals(2, response.transactions.size)
  }

  @Test
  fun `getUserInformation should throw exception if user not found`() {
    val customerId = "404"
    `when`(userService.getUserInfo(customerId)).thenReturn(null)

    val exception =
      assertThrows<ResourceNotFoundException> { accountService.getUserInformation(customerId) }

    assertTrue(exception.message!!.contains("User with provided customerId not found!"))
  }

  @Test
  fun `getUserInformation should throw exception if account not found`() {
    val customerId = "CUST0001"
    val user = User(123, customerId, "Bob", "Brown")

    `when`(userService.getUserInfo(customerId)).thenReturn(user)
    `when`(accountRepository.findByCustomerId(customerId)).thenReturn(null)

    val exception =
      assertThrows<ResourceNotFoundException> { accountService.getUserInformation(customerId) }

    assertTrue(exception.message!!.contains("Account for provided customerId not found!"))
  }
}
