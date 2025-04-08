package com.bank.accounts.controller

import com.bank.accounts.entity.Account
import com.bank.accounts.model.AccountRequest
import com.bank.accounts.model.UserInfoResponse
import com.bank.accounts.service.AccountService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {

  @Autowired private lateinit var mockMvc: MockMvc

  @Autowired private lateinit var objectMapper: ObjectMapper

  @MockitoBean private lateinit var accountService: AccountService

  @Test
  fun `createAccount should return 202 Accepted with account response`() {
    val request = AccountRequest(customerId = "123", initialCredit = 100.0)
    val account = Account(customerId = "123", balance = 100.0)

    `when`(accountService.createAccount("123", 100.0)).thenReturn(account)

    mockMvc
      .perform(
        post("/v1/accounts/create")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request))
      )
      .andExpect(status().isAccepted)
      .andExpect(jsonPath("customerId").value("123"))
      .andExpect(jsonPath("balance").value(100.0))
  }

  @Test
  fun `getUserInfo should return 200 OK with user info`() {
    val userInfo =
      UserInfoResponse(
        name = "John",
        surname = "Smith",
        balance = 100.0,
        transactions = emptyList()
      )

    `when`(accountService.getUserInformation("123")).thenReturn(userInfo)

    mockMvc
      .perform(get("/v1/accounts/customerId/123"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("name").value("John"))
      .andExpect(jsonPath("surname").value("Smith"))
      .andExpect(jsonPath("balance").value(100.0))
  }
}
