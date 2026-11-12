package com.example.tripmate.mapper

import com.example.tripmate.model.Destination
import com.example.tripmate.model.SingleDestinationDto

// Mapper pour convertir un SingleDestinationDto en Destination
fun mapDestination(dto: SingleDestinationDto): Destination {
    return Destination(
        id = dto.id,
        nom = dto.nom,
        pays = dto.pays,
        hotel = dto.hotel,
        adresse = dto.adresse,
        prix = dto.prix,
        devise = dto.devise,
        unitePrix = dto.unitePrix,
        dateArrivee = dto.dateArrivee,
        dateDepart = dto.dateDepart,
        imageUrl = dto.imageUrl,
        tags = dto.tags,
        commentaires = dto.commentaires
    )
}