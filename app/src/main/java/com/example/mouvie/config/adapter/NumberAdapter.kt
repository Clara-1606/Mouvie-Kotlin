package com.example.mouvie.config.adapter

import com.squareup.moshi.*

class NumberAdapter: JsonAdapter<Number>() {

    @ToJson
    override fun toJson(writer: JsonWriter, value: Number?) {
        writer.value(value?.toInt())
    }

    @FromJson
    override fun fromJson(reader: JsonReader): Number? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            reader.nextDouble()
        } else {
            reader.nextNull()
        }
    }
}