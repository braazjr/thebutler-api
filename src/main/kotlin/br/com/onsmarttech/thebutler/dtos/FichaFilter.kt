package br.com.onsmarttech.thebutler.dtos

import com.fasterxml.jackson.annotation.JsonFormat

data class FichaFilter(
        var idEmpresa: String?,
        val idCondominio: String?,
        val idBloco: String?,
        val numeroApartamento: String,
        val codigo: String?,
        @JsonFormat(pattern = "yyyy-MM-dd") val dataInicioDe: String?,
        @JsonFormat(pattern = "yyyy-MM-dd") val dataInicioPara: String?,
        @JsonFormat(pattern = "yyyy-MM-dd") val dataFimDe: String?,
        @JsonFormat(pattern = "yyyy-MM-dd") val dataFimPara: String?
)
