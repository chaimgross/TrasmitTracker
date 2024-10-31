package com.example.transmittrackersdk.data.local

import android.content.Context
import android.util.Log
import com.example.transmittrackersdk.data.model.ButtonEvent
import com.example.transmittrackersdk.data.model.EventEntity
import com.example.transmittrackersdk.data.model.ScreenEvent
import com.example.transmittrackersdk.data.model.UserEvent
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseEventDataSource(context: Context) : LocalEventDataSource {

    companion object {
        private const val TAG = "DatabaseSource"
    }

    private val eventDao by lazy {
        EventDatabase.getInstance(context).eventDao()
    }

    override suspend fun saveScreenSwitchEvent(screenEvent: ScreenEvent) {
        saveUserEventToDatabase(screenEvent)
    }

    override suspend fun saveButtonPressEvent(buttonEvent: ButtonEvent) {
        saveUserEventToDatabase(buttonEvent)
    }

    private suspend fun saveUserEventToDatabase(userEvent: UserEvent) {
        val event = EventEntity(
            eventType = userEvent.eventType.value,
            eventName = userEvent.eventName,
            timestamp = userEvent.timestamp,
            metaData = userEvent.metaData?.toJsonString()
        )
        withContext(Dispatchers.IO) {
            eventDao.insertEvent(event)
        }
        Log.d(TAG,"Event saved to database: $event")
    }

    private fun Map<String, Any?>.toJsonString(): String {
        return Gson().toJson(this)
    }

}