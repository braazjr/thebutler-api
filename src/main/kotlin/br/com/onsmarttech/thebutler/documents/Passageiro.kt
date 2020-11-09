package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("passageiros")
data class Passageiro(
        @Id
        var id: String?,
        @get:NotNull
        val documento: String?,
        @get:NotBlank
        val celular: String?,
        @get:NotBlank
        val nome: String?,
        @get:NotNull
        val tipoDocumento: TipoDocumento?,
        val ativo: Boolean?,
        val email: String?,
        val telefone: String?,
        val observacao: String?,
        val foto64: String?,
        val dataInicio: LocalDate?,
        val dataFim: LocalDate?,
        var empresa: EmpresaSub?,
        var documentos: MutableList<Documento> = mutableListOf(),
        val empresaId: String?,
        var dependentes: MutableList<Any> = mutableListOf(),
        val responsavelId: String?
)