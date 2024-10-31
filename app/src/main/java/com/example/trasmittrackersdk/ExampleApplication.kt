package com.example.trasmittrackersdk

import android.app.Application
import com.example.transmittrackersdk.presentation.manager.TransmitTracker

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TransmitTracker.init(this)
    }
}