package br.com.onsmarttech.thebutler.documents

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.stream.Collectors
import javax.validation.constraints.NotBlank

@Document
data class Ficha(
        var apartamento: Apartamento?,
        var moradores: List<MoradorSub>?,
        val dataInicio: LocalDate?,
        val dataFim: LocalDate?
)

data class MoradorSub(
        val id: String?,
        @get:NotBlank val email: String?,
        @get:NotBlank val nome: String?
)

fun convertMoradorToSub(morador: Morador) = MoradorSub(morador.id, morador.email, morador.nome)

fun convertMoradoresToSub(moradores: List<Morador>) =
        moradores.stream().map { convertMoradorToSub(it) }.collect(Collectors.toList())