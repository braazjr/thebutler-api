package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors
import javax.validation.constraints.NotBlank

@Document("fichas")
data class Ficha(
        @Id val id: String?,
        var apartamento: Apartamento?,
        var moradores: MutableList<MoradorSub>?,
        val dataInicio: LocalDate? = LocalDate.now(),
        val dataFim: LocalDate?,
        var documentos: MutableList<Documento>?,
        val dataCriacao: LocalDateTime?,
        val dataAlteracao: LocalDateTime?
) {
        constructor(
                apartamento: Apartamento?,
                moradores: MutableList<MoradorSub>
        ) : this(null, apartamento, moradores, null, null, null, null, null)
}

data class MoradorSub(
        val id: String?,
        val email: String?,
        @get:NotBlank val nome: String?
)

fun convertMoradorToSub(morador: Morador) = MoradorSub(morador.id, morador.email, morador.nome)

fun convertMoradoresToSub(moradores: List<Morador>): MutableList<MoradorSub>? =
        moradores.stream().map { convertMoradorToSub(it) }.collect(Collectors.toList())