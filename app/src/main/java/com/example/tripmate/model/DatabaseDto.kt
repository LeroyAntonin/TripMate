package com.example.tripmate.model

import kotlinx.serialization.Serializable

// DTO pour représenter la structure de la base de données et les deux listes destinations et journaux
@Serializable
data class DatabaseDto(
    val destinations: List<Destination> = emptyList(),
    val journaux: List<Journal> = emptyList(),
)
