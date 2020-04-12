package br.com.onsmarttech.thebutler.documents

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Ficha(
        val apartamento: Apartamento,
        var moradores: List<Morador>
)