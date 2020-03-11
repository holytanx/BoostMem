package com.example.boostmem.Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {

    @TypeConverter
    open fun fromString(value: String?): ArrayList<String?>? {
        val listType: Type =
            object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    open fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}