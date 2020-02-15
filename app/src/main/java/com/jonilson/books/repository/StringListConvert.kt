package com.jonilson.books.repository

import androidx.room.TypeConverter

class StringListConvert {
    @TypeConverter
    fun stringToList(s: String?): List<String>? =
        s?.split(',')

    @TypeConverter
    fun listToString(list: List<String>?): String? =
        list?.joinToString()
}