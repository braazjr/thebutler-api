package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Morador
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class FichaDto(
        @get:NotBlank val idApartamento: String,
        @get:Valid @get:NotEmpty val moradores: List<Morador>
)