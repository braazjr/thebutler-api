package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Morador
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class FichaDto(
        @get:NotBlank val idApartamento: String,
        @get:Valid @get:NotEmpty val moradores: List<Morador>,
        @get:NotNull @JsonFormat(pattern = "dd/MM/yyyy") val dataInicio: LocalDate
)