package com.bank.accounts.controller

import com.bank.accounts.exception.ResourceNotFoundException
import com.bank.accounts.model.AccountRequest
import com.bank.accounts.model.AccountResponse
import com.bank.accounts.service.AccountService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/accounts")
@Tag(name = "Account API", description = "API for managing customer current accounts")
class AccountController(private val accountService: AccountService) {

  @PostMapping("/create")
  @Operation(summary = "Create a new current account for a customer")
  @ApiResponses(
    value =
      [
        ApiResponse(responseCode = "201", description = "Account created successfully"),
        ApiResponse(responseCode = "404", description = "User not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
      ]
  )
  fun createAccount(@RequestBody request: AccountRequest): ResponseEntity<*> {
    try {
      val account = accountService.createAccount(request.customerId, request.initialCredit)
      val response = AccountResponse(customerId = account.customerId, balance = account.balance)
      return ResponseEntity.accepted().body(response)
    } catch (e: ResourceNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    } catch (e: Exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }
  }

  @GetMapping("/customerId/{customerId}")
  @Operation(summary = "Get user info with balance and transactions")
  @ApiResponses(
    value =
      [
        ApiResponse(responseCode = "200", description = "User info retrieved successfully"),
        ApiResponse(responseCode = "404", description = "User or account not found"),
        ApiResponse(responseCode = "500", description = "Internal server error")
      ]
  )
  fun getUserInfo(@PathVariable customerId: String): ResponseEntity<*> {
    try {
      val response = accountService.getUserInformation(customerId)
      return ResponseEntity.ok(response)
    } catch (e: ResourceNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    } catch (e: Exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
    }
  }
}
