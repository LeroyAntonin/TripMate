package com.example.tripmate.model

import kotlinx.serialization.Serializable

data class JournalDto(val journal: List<SingleJournalDto>)


data class SingleJournalDto(
    val id: Int,
    val ville: String,
    val pays: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val avis: String
)

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