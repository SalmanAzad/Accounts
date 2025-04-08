package com.bank.accounts.repository

import com.bank.accounts.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {
  fun findByCustomerId(customerId: String): Account?
}
