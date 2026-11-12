package com.example.tripmate.model

import kotlinx.serialization.Serializable

// Class représentant une destination avec ses détails
@Serializable
data class Destination(
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
