package com.ccmgolf.kitsune.data.converters

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeConverter {
    companion object {
        @TypeConverter
        fun fromDate(date: Date): String {
            return SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            ).format(date)
        }

        @TypeConverter
        fun fromString(dateString: String): Date {
            try {
                return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(
                    dateString
                )
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return Date()
        }
    }


}