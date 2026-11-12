package com.example.tripmate.api

import com.example.tripmate.model.Destination
import com.example.tripmate.model.Journal
import com.example.tripmate.model.NewJournal
import com.example.tripmate.network.KtorClient
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive

object DatabaseApi {
    private const val BASE_URL = "http://10.13.112.184:3000"  // adresse fac : 10.13.112.184

    private fun JsonElement.intId(resource: String): Int =
        jsonPrimitive.content.toIntOrNull()
            ?: error("ID invalide pour $resource : ${jsonPrimitive.content}")

    suspend fun getDestinations(): List<Destination> =
        KtorClient.get<List<RemoteDestination>>("$BASE_URL/destinations")
            .map { it.toDestination() }

    suspend fun getJournaux(): List<Journal> =
        KtorClient.get<List<RemoteJournal>>("$BASE_URL/journaux")
            .map { it.toJournal() }

    suspend fun addJournal(journal: NewJournal): Journal {
        val nextId = getJournaux()
            .maxOfOrNull { it.id }
            ?.let { it + 1 }
            ?: 1

        return KtorClient.post<RemoteJournal>("$BASE_URL/journaux") {
            setBody(journal.copy(id = nextId))
        }.toJournal()
    }

    suspend fun updateJournal(journal: NewJournal): Journal =
        KtorClient.patch<RemoteJournal>("$BASE_URL/journaux/${journal.id}") {
            setBody(journal)
        }.toJournal()

    suspend fun deleteJournal(journalId: Int) {
        KtorClient.delete("$BASE_URL/journaux/$journalId")
    }

    suspend fun addDestinationComment(
        destinationId: Int,
        commentaire: String,
        commentairesExistants: List<String>,
    ): Destination {
        return KtorClient.patch<RemoteDestination>("$BASE_URL/destinations/$destinationId") {
            setBody(
                DestinationComments(
                    commentaires = commentairesExistants + commentaire,
                )
            )
        }.toDestination()
    }

    @Serializable
    private data class DestinationComments(
        val commentaires: List<String>,
    )

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
            id = id.intId("destination"),
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
            id = id.intId("journal"),
            ville = ville,
            pays = pays,
            dateArrivee = dateArrivee,
            dateDepart = dateDepart,
            imageUrl = imageUrl,
            avis = avis,
        )
    }
}
