package com.bank.accounts.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig {
  @Bean
  fun apiInfo(): OpenAPI =
    OpenAPI()
      .info(
        Info()
          .title("Current Account API")
          .description("API for managing customer current accounts")
          .version("1.0.0")
      )
}
