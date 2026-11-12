package com.example.tripmate.network

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// StateManager identique à celui de Netfloux
object StateManager {

    @OptIn(DelicateCoroutinesApi::class)
    fun launchCoroutine(block: suspend () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            block()
        }
    }

}