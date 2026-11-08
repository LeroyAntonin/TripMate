package com.example.tripmate.mapper

import com.example.tripmate.model.Destination
import com.example.tripmate.model.Journal
import com.example.tripmate.model.SingleDestinationDto
import com.example.tripmate.model.SingleJournalDto

fun mapJournal(dto: SingleJournalDto): Journal {
    return Journal(
        id = dto.id,
        ville = dto.ville,
        pays = dto.pays,
        dateArrivee = dto.dateArrivee,
        dateDepart = dto.dateDepart,
        imageUrl = dto.imageUrl,
        avis = dto.avis
    )
}