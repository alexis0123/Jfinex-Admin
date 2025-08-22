package com.jfinex.admin.data.local

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

class StudentMapConverter {

    private val mapSerializer = MapSerializer(String.serializer(), Int.serializer())

    @TypeConverter
    fun fromMap(map: Map<String, Int>): String {
        return Json.encodeToString(mapSerializer, map)
    }

    @TypeConverter
    fun toMap(data: String): Map<String, Int> {
        return Json.decodeFromString(mapSerializer, data)
    }
}
