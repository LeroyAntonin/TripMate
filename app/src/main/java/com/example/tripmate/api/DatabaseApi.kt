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

    // Convertit un JsonElement en Int, en lançant une exception si la conversion échoue.
    // Permet de gérer les erreurs d'ID dans les destinations et les journaux.
    private fun JsonElement.intId(resource: String): Int =
        jsonPrimitive.content.toIntOrNull()
            ?: error("ID invalide pour $resource : ${jsonPrimitive.content}")

    //Récupère les destinations depuis l'API
    suspend fun getDestinations(): List<Destination> =
        KtorClient.get<List<RemoteDestination>>("$BASE_URL/destinations")
            .map { it.toDestination() }

    //Récupère les journaux depuis l'API
    suspend fun getJournaux(): List<Journal> =
        KtorClient.get<List<RemoteJournal>>("$BASE_URL/journaux")
            .map { it.toJournal() }

    // Ajoute un nouveau journal à l'API et crée son ID
    suspend fun addJournal(journal: NewJournal): Journal {
        // json-server ne génère pas automatiquement l'identifiant dans ce projet.
        val nextId = getJournaux()
            .maxOfOrNull { it.id }
            ?.let { it + 1 }
            ?: 1

        return KtorClient.post<RemoteJournal>("$BASE_URL/journaux") {
            setBody(journal.copy(id = nextId))
        }.toJournal()
    }

    // Met à jour un journal existant
    suspend fun updateJournal(journal: NewJournal): Journal =
        // Le PATCH conserve les champs du journal qui ne sont pas modifiés.
        KtorClient.patch<RemoteJournal>("$BASE_URL/journaux/${journal.id}") {
            setBody(journal)
        }.toJournal()

    // Supprime un journal existant
    suspend fun deleteJournal(journalId: Int) {
        KtorClient.delete("$BASE_URL/journaux/$journalId")
    }

    // Ajoute un commentaire à une destination existante
    suspend fun addDestinationComment(
        destinationId: Int,
        commentaire: String,
        commentairesExistants: List<String>,
    ): Destination {
        // Réenvoie la liste complète pour conserver les commentaires existants.
        return KtorClient.patch<RemoteDestination>("$BASE_URL/destinations/$destinationId") {
            setBody(
                DestinationComments(
                    commentaires = commentairesExistants + commentaire,
                )
            )
        }.toDestination()
    }

    // Classe pour les commentaires d'une destination
    @Serializable
    private data class DestinationComments(
        val commentaires: List<String>,
    )

    // Classe pour la sérialisation des destinations
    @Serializable
    private data class RemoteDestination(
        val id: JsonElement,  // Pas Int car l'API avait des bugs sur les types d'ID
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
            id = id.intId("destination"),  // Convertit en Int
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

    // Classe pour la sérialisation des journaux
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
