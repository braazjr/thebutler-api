package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotNull

@Document("empresas")
data class Empresa(

        @Id val id: String?,
        @get:NotNull val cnpj: String?,
        @get:NotNull val nomeFantasia: String?,
        @get:NotNull val razaoSocial: String?,
        val ativo: Boolean = true,
        @get:NotNull val bairro: String?,
        @get:NotNull val cep: String?,
        @get:NotNull val cidade: String?,
        val complemento: String?,
        val email: String?,
        @get:NotNull val estado: String?,
        val numero: Int?,
        @get:NotNull val rua: String?,
        @get:NotNull val telefone: String?,
        @get:NotNull var registradoPor: UsuarioSub?,
        val empresaConfig: EmpresaConfig?
)

data class EmpresaConfig(
        @get:NotNull val qtyApartamentos: Int?,
        val temCracha: Boolean = false,
        var apartamentosCadastrados: Long?
)
