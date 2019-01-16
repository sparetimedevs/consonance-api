package com.sparetimedevs.consonance.json.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.bson.types.ObjectId

class CustomObjectIdSerializer(handledType: Class<ObjectId>) : StdSerializer<ObjectId>(handledType) {
    override fun serialize(objectId: ObjectId, jsonGenerator: JsonGenerator, serializer: SerializerProvider) {
        jsonGenerator.writeString(objectId.toHexString())
    }
}
