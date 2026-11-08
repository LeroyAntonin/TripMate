package com.example.tripmate.api

import com.example.tripmate.data.loadDestinations
import com.example.tripmate.data.loadJournal
import com.example.tripmate.model.Destination
import com.example.tripmate.model.Journal


object JournalApi {
    private val journaux: List<Journal> by lazy { loadJournal() }

    fun getJournal(filter: String = ""): List<Journal> {
        return journaux.filter { it.ville.contains(filter, ignoreCase = true) }
    }
    fun findById(id: Int?): Journal? {
        return journaux.firstOrNull { it.id == id }
    }

}