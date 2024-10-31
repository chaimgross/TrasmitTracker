package com.example.transmittrackersdk.domain.usecase

import com.example.transmittrackersdk.data.model.ButtonEvent
import com.example.transmittrackersdk.data.repository.UserInteractionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class TrackButtonPressUseCase(
    private val userInteractionRepository: UserInteractionRepository,
    private val coroutineScope: CoroutineScope = CoroutineScope(kotlinx.coroutines.Dispatchers.IO)) {

    fun execute(buttonText: String, buttonId: String?, buttonTag: String?) {
        val screenEvent = ButtonEvent(
            eventName = buttonText,
            id = buttonId,
            tag = buttonTag,
            timestamp = System.currentTimeMillis()
        )

        coroutineScope.launch {
            userInteractionRepository.handleButtonPress(screenEvent)
        }
    }
}