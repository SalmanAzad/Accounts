package com.bank.accounts.repository

import com.bank.accounts.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
  fun findByCustomerId(customerId: String): User?
}
