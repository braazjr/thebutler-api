package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Apartamento(
        @Id var id: String?,
        val numero: String?,
        val ativo: Boolean = false,
        val numeroQuartos: Int?,
        val bloco: BlocoSub?,
        val observacao: String?,
        val registradoPor: UsuarioSub?
)

data class BlocoSub(
        var id: String?,
        val nome: String?,
        val numero: Int?
)