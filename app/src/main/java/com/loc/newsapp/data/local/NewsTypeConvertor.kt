package com.loc.newsapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.loc.newsapp.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConvertor {

    @TypeConverter
    fun sourceToString(source : Source) : String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(string : String) : Source {
        return string.split(",").let {
            Source(it[0], it[1])
        }
    }
}