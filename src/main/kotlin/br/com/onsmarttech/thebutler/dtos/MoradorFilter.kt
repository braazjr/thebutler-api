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

    fun isNull() = this.empresaId.isNullOrBlank() && this.condominioId.isNullOrBlank() && this.blocoId.isNullOrBlank()
            && this.blocoId.isNullOrBlank() && this.nome.isNullOrBlank() && this.documento.isNullOrBlank()
            && this.apartamentoNumero.isNullOrBlank()
}
