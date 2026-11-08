package com.example.tripmate.model

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