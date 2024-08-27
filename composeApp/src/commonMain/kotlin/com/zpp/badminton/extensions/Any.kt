package com.zpp.badminton.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/*
 * @Author :Chao Park
 * @Date : on 2024-08-22
 * @Describe :
 **/

fun LocalDateTime.dateString(): String {
  return "${this.year}-${this.monthNumber}-${this.dayOfMonth}"
}

fun currentDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun Long.toDate() = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())


