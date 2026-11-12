package com.example.tripmate.model

data class DestinationDto(val destinations: List<SingleDestinationDto>)

// DTO pour représenter une destination individuelle
data class SingleDestinationDto(
    val id: Int,
    val nom: String,
    val pays: String,
    val hotel: String,
    val adresse: String,
    val prix: Int,
    val devise : String,
    val unitePrix: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val tags: List<String>,
    val commentaires: List<String>,
)