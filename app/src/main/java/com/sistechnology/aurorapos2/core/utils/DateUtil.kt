package com.sistechnology.aurorapos2.core.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

sealed class DateUtil {
    companion object{
        fun getCurrentDateAsString() : String {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        fun getCurrentTimeAsString() : String{
            return getCurrentDateTimeAsString().split(" ")[1]
        }
        fun getCurrentDateTimeAsString() : String {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        fun getCurrentDateTimeWithPatternAndDate(localDateTime: LocalDateTime, pattern: SimpleDateFormat) : String{
            return pattern.format(localDateTime)

        }
    }
}