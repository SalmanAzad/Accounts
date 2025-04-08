package com.bank.accounts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class AbcApplication

fun main(args: Array<String>) {
  runApplication<AbcApplication>(*args)
}
