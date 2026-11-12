package com.example.tripmate.model

import kotlinx.serialization.Serializable

data class JournalDto(val journal: List<SingleJournalDto>)

// DTO pour représenter un journal individuel
data class SingleJournalDto(
    val id: Int,
    val ville: String,
    val pays: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val avis: String
)

// DTO pour représenter un nouveau journal à créer
@Serializable
data class NewJournal(
    val id: Int,
    val ville: String,
    val pays: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val avis: String,
)