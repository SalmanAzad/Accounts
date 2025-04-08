package com.bank.accounts.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

fun createObjectMapper(): ObjectMapper {
  return jacksonObjectMapper()
    .registerModule(JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
}

fun <T : Any> T.createLogger(): Logger = LogManager.getLogger(javaClass)
