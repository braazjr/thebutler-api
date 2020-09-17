package br.com.onsmarttech.thebutler.dtos

data class ApartamentoFilter(
        var empresaId: String = "",
        val idCondominio: String = "",
        val idBloco: String = "",
        val numero: String = ""
)