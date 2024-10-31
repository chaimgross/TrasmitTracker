package com.example.transmittrackersdk.data.repository

import android.content.Context
import com.example.transmittrackersdk.data.local.DatabaseEventDataSource
import com.example.transmittrackersdk.data.local.FileEventDataSource
import com.example.transmittrackersdk.data.local.LocalEventDataSource
import com.example.transmittrackersdk.data.model.ButtonEvent
import com.example.transmittrackersdk.data.model.ScreenEvent

class UserInteractionRepository(
    private val context: Context,
    private val localEventDataSources: List<LocalEventDataSource> = listOf(DatabaseEventDataSource(context), FileEventDataSource(context)),
) {

    suspend fun handleScreenSwitch(screenEvent: ScreenEvent) {
        localEventDataSources.forEach {
            it.saveScreenSwitchEvent(screenEvent)
        }
    }

    suspend fun handleButtonPress(buttonEvent: ButtonEvent) {
        localEventDataSources.forEach {
            it.saveButtonPressEvent(buttonEvent)
        }
    }
}