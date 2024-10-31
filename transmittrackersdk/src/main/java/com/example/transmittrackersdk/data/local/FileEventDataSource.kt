package com.example.transmittrackersdk.data.local

import android.content.Context
import android.util.Log
import com.example.transmittrackersdk.data.model.ButtonEvent
import com.example.transmittrackersdk.data.model.ScreenEvent
import com.example.transmittrackersdk.data.model.UserEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter
import java.io.IOException

class FileEventDataSource(private val context: Context) : LocalEventDataSource {

    companion object {
        private const val TAG = "FileSource"
    }

    private val file: File by lazy {
        File(context.filesDir, "user_events_log.txt").apply {
            if (!exists()) {
                createNewFile()
            }
        }
    }

    override suspend fun saveScreenSwitchEvent(screenEvent: ScreenEvent) {
        saveUserEventToFile(screenEvent)
    }

    override suspend fun saveButtonPressEvent(buttonEvent: ButtonEvent) {
        saveUserEventToFile(buttonEvent)
    }

    private suspend fun saveUserEventToFile(userEvent: UserEvent) {
        val logEntry = getLogEntry(userEvent)

        withContext(Dispatchers.IO) {
            try {
                FileWriter(file, true).use { writer ->
                    writer.append(logEntry)
                }
                Log.d(TAG, "Event logged to file: $logEntry")
            } catch (e: IOException) {
                Log.e(TAG, "Error writing event to file", e)
            }
        }
    }

    private fun getLogEntry(userEvent: UserEvent): String {
        return buildString {
            appendLine("----- Log Entry -----")
            appendLine("Timestamp: ${userEvent.timestamp}")
            appendLine("Event Type: ${userEvent.eventType.value}")
            appendLine("Event Name: ${userEvent.eventName}")
            appendLine("Metadata: ${userEvent.metaData}")
            appendLine("OS Version: ${userEvent.osVersion}")
            appendLine("---------------------")
        }
    }
}