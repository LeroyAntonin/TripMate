package com.example.tripmate.data

import com.example.tripmate.mapper.mapDestination
import com.example.tripmate.model.Destination
import com.example.tripmate.model.DestinationDto
import com.example.tripmate.model.SingleDestinationDto
import org.json.JSONObject

const val destinationData = """
    
    
    {
  "destinations": [
    {
      "id": 1,
      "nom": "Bali",
      "pays": "Indonésie",
      "hotel": "Ubud Rice Terrace Resort",
      "adresse": "Jalan Raya Tegalalang, 80561",
      "prix": 89,
      "devise": "EUR",
      "unitePrix": " personne",
      "dateArrivee": "2027-11-02",
      "dateDepart": "2027-11-07",
      "imageUrl": "https://picsum.photos/seed/bali/240/360",
      "tags": ["Plage", "Nature", "Bien-être"],
      "commentaires": [
        "Cadre verdoyant et très reposant.",
        "Les massages proposés au spa sont excellents."
      ]
    },
    {
      "id": 2,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "https://picsum.photos/seed/santorin/240/360",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    },
    {
      "id": 3,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "https://picsum.photos/seed/santorin/240/360",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    },
    {
      "id": 4,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "https://picsum.photos/seed/santorin/240/360",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    },
    {
      "id": 5,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "https://picsum.photos/seed/santorin/240/360",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    },
    {
      "id": 2,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "https://picsum.photos/seed/santorin/240/360",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    },
    {
      "id": 2,
      "nom": "Santorin",
      "pays": "Grèce",
      "hotel": "Caldera View Suites",
      "adresse": "12 Oia Main Street, 84702",
      "prix": 210,
      "devise": "EUR",
      "unitePrix": "personne",
      "dateArrivee": "2027-06-22",
      "dateDepart": "2027-06-27",
      "imageUrl": "https://picsum.photos/seed/santorin/240/360",
      "tags": ["Plage", "Romantique", "Paysages"],
      "commentaires": [
        "Le coucher de soleil depuis la terrasse est magnifique.",
        "Suite très calme avec une vue exceptionnelle."
      ]
    }
  ],
  "journaux": []
}
"""

fun loadDestinations(): List<Destination> {
    val array = JSONObject(destinationData).getJSONArray("destinations")
    val entries = List(array.length()) { index ->
    val item = array.getJSONObject(index)

    val tagArray = item.getJSONArray("tags")
    val tags = List(tagArray.length()) { tagIndex ->
        tagArray.getString(tagIndex)
    }

    val commentaireArray = item.getJSONArray("commentaires")
    val commentaires = List(commentaireArray.length()) { commentaireIndex ->
        commentaireArray.getString(commentaireIndex)
    }

    SingleDestinationDto(
        id = item.getInt("id"),
        nom = item.getString("nom"),
        pays = item.getString("pays"),
        hotel = item.getString("hotel"),
        adresse = item.getString("adresse"),
        prix = item.getInt("prix"),
        devise = item.getString("devise"),
        unitePrix = item.getString("unitePrix"),
        dateArrivee = item.getString("dateArrivee"),
        dateDepart = item.getString("dateDepart"),
        imageUrl = item.getString("imageUrl"),
        tags = tags,
        commentaires = commentaires,
    )
}
    
val dto = DestinationDto(destinations = entries)
return dto.destinations.map { entry -> mapDestination(entry) }
}