package com.mate.carpool.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mate.carpool.AutoLoginPreferences
import java.io.InputStream
import java.io.OutputStream

object AutoLoginPreferencesSerializer : Serializer<AutoLoginPreferences> {

    override val defaultValue: AutoLoginPreferences = AutoLoginPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AutoLoginPreferences {
        return try {
            AutoLoginPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: AutoLoginPreferences, output: OutputStream) = t.writeTo(output)
}