package com.example.tripmate.api

import com.example.tripmate.data.loadDestinations
import com.example.tripmate.model.Destination



object DestinationsAPI {
    private val destinations: List<Destination> by lazy { loadDestinations() }

    fun getDestinations(filter: String = ""): List<Destination> {
    return destinations.filter { it.nom.contains(filter, ignoreCase = true) }
}
    fun findById(id: Int?): Destination? {
    return destinations.firstOrNull { it.id == id }
}

    }