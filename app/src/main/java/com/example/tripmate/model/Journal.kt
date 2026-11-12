package com.example.tripmate.model

import kotlinx.serialization.Serializable

// Class représentant un journal avec ses détails
@Serializable
data class Journal(
    val id: Int,
    val ville: String,
    val pays: String,
    val dateArrivee: String,
    val dateDepart: String,
    val imageUrl: String,
    val avis: String
)