package com.example.transmittrackersdk.data.local

import com.example.transmittrackersdk.data.model.ButtonEvent
import com.example.transmittrackersdk.data.model.ScreenEvent

interface LocalEventDataSource {
     suspend fun saveScreenSwitchEvent(screenEvent: ScreenEvent)
     suspend fun saveButtonPressEvent(buttonEvent: ButtonEvent)
}