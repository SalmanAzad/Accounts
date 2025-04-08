package com.bank.accounts.service

import com.bank.accounts.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
  fun getUserInfo(customerId: String) = userRepository.findByCustomerId(customerId)
}
