package com.example.tripmate.model

import kotlinx.serialization.Serializable

@Serializable
data class DatabaseDto(
    val destinations: List<Destination> = emptyList(),
    val journaux: List<Journal> = emptyList(),
)
