package com.example.tripmate.data

import com.example.tripmate.mapper.mapJournal
import com.example.tripmate.model.Journal
import com.example.tripmate.model.JournalDto
import com.example.tripmate.model.SingleDestinationDto
import com.example.tripmate.model.SingleJournalDto
import org.json.JSONObject

const val journalData = """
    
    
    {
  "journaux": [
    {
      "id": 10,
      "ville": "Marseille",
      "pays": "France",
      "dateArrivee": "12/04/2026",
      "dateDepart": "16/04/2026",
      "imageUrl": "https://cdn.generationvoyage.fr/2025/07/Le-Vieux-Port-Marseille-630x420.jpeg",
      "avis": "Promenade sur le Vieux-Port, excursion dans les calanques et découverte de la cuisine méditerranéenne. Marseille est une ville vivante et ensoleillée, avec un patrimoine maritime particulièrement intéressant."
    },
    {
      "id": 20,
      "ville": "Amsterdam",
      "pays": "Pays-Bas",
      "dateArrivee": "03/05/2026",
      "dateDepart": "07/05/2026",
      "imageUrl": "https://tse1.mm.bing.net/th/id/OIP.IINb1mT8QrzNJDXxL5rGSgHaE_?r=0&rs=1&pid=ImgDetMain&o=7&rm=3",
      "avis": "Balade à vélo le long des canaux, visite du musée Van Gogh et promenade dans le quartier Jordaan. Amsterdam est agréable à parcourir à vélo et possède une ambiance culturelle très riche."
    },
    {
      "id": 30,
      "ville": "Prague",
      "pays": "République tchèque",
      "dateArrivee": "18/05/2026",
      "dateDepart": "22/05/2026",
      "imageUrl": "https://tse3.mm.bing.net/th/id/OIP.vM6aG0e63AI2QBdm0ppDCgHaDx?r=0&rs=1&pid=ImgDetMain&o=7&rm=3",
      "avis": "Visite du château de Prague, traversée du pont Charles et découverte de la vieille ville. Prague est une ville élégante et historique, dont l'architecture est particulièrement remarquable."
    },
    {
      "id": 40,
      "ville": "Vienne",
      "pays": "Autriche",
      "dateArrivee": "07/06/2026",
      "dateDepart": "11/06/2026",
      "imageUrl": "https://tse4.mm.bing.net/th/id/OIP.AjGoYIGyAlsrVgekT3avuQHaE1?r=0&rs=1&pid=ImgDetMain&o=7&rm=3",
      "avis": "Visite du palais de Schönbrunn, découverte des musées du MuseumsQuartier et dégustation d'une sachertorte. Vienne est raffinée et reposante, avec une scène artistique et musicale exceptionnelle."
    },
    {
      "id": 50,
      "ville": "Dublin",
      "pays": "Irlande",
      "dateArrivee": "22/06/2026",
      "dateDepart": "26/06/2026",
      "imageUrl": "https://www.urlaubsguru.de/wp-content/uploads/2015/01/dublin-city-skyline-istock_000079625065_large-2.jpg",
      "avis": "Visite du Trinity College, découverte du quartier de Temple Bar et excursion sur la côte irlandaise. Dublin est conviviale et chaleureuse, avec une culture locale très présente."
    },
    {
      "id": 60,
      "ville": "Berlin",
      "pays": "Allemagne",
      "dateArrivee": "10/07/2026",
      "dateDepart": "14/07/2026",
      "imageUrl": "https://cdn.getyourguide.com/img/tour/58befcc740613.jpeg/148.jpg",
      "avis": "Visite de l'East Side Gallery, découverte de l'île aux Musées et promenade dans le Tiergarten. Berlin est une capitale dynamique et créative, marquée par une histoire fascinante."
    },
    {
      "id": 70,
      "ville": "Sydney",
      "pays": "Australie",
      "dateArrivee": "04/09/2026",
      "dateDepart": "10/09/2026",
      "imageUrl": "https://visitsydney.com/files/2018/10/visit-sydney.jpg",
      "avis": "Visite de l'Opéra, randonnée le long de la côte et baignade à Bondi Beach. Sydney offre un équilibre exceptionnel entre vie urbaine, plages et paysages naturels."
    },
    {
      "id": 80,
      "ville": "Vancouver",
      "pays": "Canada",
      "dateArrivee": "19/09/2026",
      "dateDepart": "25/09/2026",
      "imageUrl": "https://www.tripsavvy.com/thmb/uqNzAoqBIraz1RIKIPklL3H-z5Y=/2270x1321/filters:fill(auto,1)/GettyImages-629829924-5bdb57f74cedfd0026ae431f.jpg",
      "avis": "Randonnée dans Stanley Park, découverte de Granville Island et excursion dans les montagnes voisines. Vancouver est une destination accueillante où la nature est facilement accessible depuis la ville."
    },
    {
      "id": 90,
      "ville": "Le Cap",
      "pays": "Afrique du Sud",
      "dateArrivee": "08/10/2026",
      "dateDepart": "13/10/2026",
      "imageUrl": "https://media.routard.com/image/94/8/fb-cape-town.1472948.jpg",
      "avis": "Ascension de la montagne de la Table, visite du quartier de Bo-Kaap et découverte de la péninsule du Cap. Le Cap séduit par ses paysages spectaculaires, son climat agréable et sa diversité culturelle."
    },
    {
      "id": 100,
      "ville": "Séoul",
      "pays": "Corée du Sud",
      "dateArrivee": "02/11/2026",
      "dateDepart": "09/11/2026",
      "imageUrl": "https://content.r9cdn.net/rimg/dimg/30/0c/6318617a-city-35982-163ff913019.jpg?width=1366&height=768&xhint=2421&yhint=1876&crop=true",
      "avis": "Visite du palais de Gyeongbokgung, découverte des quartiers de Hongdae et dégustation de spécialités coréennes. Séoul est une métropole moderne et énergique qui conserve un patrimoine traditionnel remarquable."
    }
  ]
}
"""

fun loadJournal(): List<Journal> {
    val array = JSONObject(journalData).getJSONArray("journaux")
    val entries = List(array.length()) { index ->
        val item = array.getJSONObject(index)


        SingleJournalDto(
            id = item.getInt("id"),
            ville = item.getString("ville"),
            pays = item.getString("pays"),
            dateArrivee = item.getString("dateArrivee"),
            dateDepart = item.getString("dateDepart"),
            imageUrl = item.getString("imageUrl"),
            avis = item.getString("avis")
        )
    }

    val dto = JournalDto(journal = entries)
    return dto.journal.map { entry -> mapJournal(entry) }
}