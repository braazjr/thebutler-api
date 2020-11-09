package br.com.onsmarttech.thebutler.dtos

import java.time.LocalDate

data class PassageiroFilter(
        val documento: String?,
        val nome: String?,
        val email: String?,
        val ativo: Boolean?,
        val dataInicio: LocalDate?,
        val dataFim: LocalDate?,
        var empresaId: String?
) {
    constructor() : this(null, null, null, null, null, null, null)
}