package br.com.onsmarttech.thebutler.dtos

data class MoradorFilter(
        var empresaId: String?,
        val condominioId: String?,
        val blocoId: String?,
        val nome: String?,
        val documento: String?,
        val apartamentoNumero: String?
) {
    constructor() : this(null, null, null, null, null, null)
    constructor(empresaId: String?) : this(empresaId, null, null, null, null, null)
}
