package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.documents.Documento
import br.com.onsmarttech.thebutler.documents.Morador
import java.time.LocalDate

data class FichaFullResponseDto(
        val id: String,
        val apartamento: Apartamento?,
        val moradores: List<Morador>?,
        val dataInicio: LocalDate? = LocalDate.now(),
        val dataFim: LocalDate?,
        val documentos: MutableList<Documento>?
)