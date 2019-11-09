package nl.hva.fdmci.rockpaperscissors.database

import androidx.room.TypeConverter

class Converter {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromString(value: String): List<String> {
            return value.split(",")
        }

        @TypeConverter
        @JvmStatic
        fun toString(value: List<String>): String {
            return value.toString()
        }
    }

}