package com.example.myapplication.data.network.dto

import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.longOrNull
import com.example.myapplication.data.local.MessageEntity
import com.example.myapplication.domain.Message

fun MessageDto.toDomain(): Message? {
    // Extract the long value from JsonElement safely
    val time = (createdAt as? JsonPrimitive)?.longOrNull ?: return null

    return Message(
        id = id ?: "",
        sender = sender ?: "Unknown",
        text = text ?: "",
        createdAt = time
    )
}

fun List<MessageDto>.toDomain(): List<Message> =
    mapNotNull { it.toDomain() }

fun Message.toEntity(): MessageEntity = MessageEntity(
    id = id,
    sender = sender,
    text = text,
    createdAt = createdAt
)

fun MessageEntity.toDomain(): Message = Message(
    id = id,
    sender = sender,
    text = text,
    createdAt = createdAt
)

fun List<MessageEntity>.entitiesToDomain(): List<Message> =
    map { it.toDomain() }
