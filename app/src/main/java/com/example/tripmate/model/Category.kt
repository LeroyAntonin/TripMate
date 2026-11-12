package com.example.tripmate.model


import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Int,
    val nom: String,
    val ordre: Int,
    val tags: List<String>
)