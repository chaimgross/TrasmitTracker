package com.example.transmittrackersdk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event_table")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val eventType: String,
    val eventName: String,
    val timestamp: Long,
    val metaData: String? = null
)