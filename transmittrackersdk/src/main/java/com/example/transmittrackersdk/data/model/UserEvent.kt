package com.example.transmittrackersdk.data.model

import android.os.Build

sealed class UserEvent(
    open val eventType: EventType,
    open val eventName: String,
    open val timestamp: Long = System.currentTimeMillis(),
    open val osVersion: String? = Build.VERSION.RELEASE,
    open val metaData: Map<String, String?>? = null
)

data class ScreenEvent(
    override val eventName: String,// Activity name
    override val timestamp: Long = System.currentTimeMillis(),
) : UserEvent(EventType.SCREEN_SWITCH, eventName, timestamp)

data class ButtonEvent(
    override val eventName: String, // Button name
    override val timestamp: Long = System.currentTimeMillis(),
    val tag: String? = null,
    val id: String? = null
) : UserEvent(EventType.BUTTON_PRESS, eventName, timestamp, metaData = mapOf("tag" to tag, "id" to id))