package com.erbe.learningcompanion.protostore

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.erbe.learningcompanion.data.FilterOption
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class FilterSerializer : Serializer<FilterOption> {

    override fun readFrom(input: InputStream): FilterOption {
        try {
            return FilterOption.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override fun writeTo(t: FilterOption, output: OutputStream) {
        t.writeTo(output)
    }

    val defaultValue: FilterOption = FilterOption.getDefaultInstance()
}