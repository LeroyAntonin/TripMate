package com.example.tripmate.api

import com.example.tripmate.model.Destination
import com.example.tripmate.model.Journal
import com.example.tripmate.network.KtorClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

object DatabaseApi {
    private const val BASE_URL = "http://192.168.0.28:3000"

    suspend fun getDestinations(): List<Destination> =
        KtorClient.get<List<RemoteDestination>>("$BASE_URL/destinations")
            .map { it.toDestination() }

    suspend fun getJournaux(): List<Journal> =
        KtorClient.get<List<RemoteJournal>>("$BASE_URL/journaux")
            .map { it.toJournal() }

    @Serializable
    private data class RemoteDestination(
        val id: JsonElement,
        val nom: String,
        val pays: String,
        val hotel: String,
        val adresse: String,
        val prix: Int,
        val devise: String,
        val unitePrix: String,
        val dateArrivee: String,
        val dateDepart: String,
        val imageUrl: String,
        val tags: List<String>,
        val commentaires: List<String>,
    ) {
        fun toDestination() = Destination(
            id = id.jsonPrimitive.content.toInt(),
            nom = nom,
            pays = pays,
            hotel = hotel,
            adresse = adresse,
            prix = prix,
            devise = devise,
            unitePrix = unitePrix,
            dateArrivee = dateArrivee,
            dateDepart = dateDepart,
            imageUrl = imageUrl,
            tags = tags,
            commentaires = commentaires,
        )
    }

    @Serializable
    private data class RemoteJournal(
        val id: JsonElement,
        val ville: String,
        val pays: String,
        val dateArrivee: String,
        val dateDepart: String,
        val imageUrl: String,
        val avis: String,
    ) {
        fun toJournal() = Journal(
            id = id.jsonPrimitive.content.toInt(),
            ville = ville,
            pays = pays,
            dateArrivee = dateArrivee,
            dateDepart = dateDepart,
            imageUrl = imageUrl,
            avis = avis,
        )
    }
}
