package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Document
data class Empresa(

        @Id val id: String = "",
        @get:NotNull @get:Size(min = 18, max = 18) val cnpj: String,
        @get:NotNull @get:Size(min = 3, max = 150) val nomeFantasia: String,
        @get:NotNull @get:Size(min = 5, max = 150) val razaoSocial: String,
        val ativo: Boolean,
        @get:NotNull @get:Size(min = 5, max = 30) val bairro: String,
        @get:NotNull @get:Size(min = 9, max = 9) val cep: String,
        @get:NotNull @get:Size(min = 5, max = 30) val cidade: String,
        val complemento: String,
        @get:Size(min = 5, max = 50) val email: String,
        @get:NotNull @get:Size(min = 2, max = 2) val estado: String,
        val numero: Int,
        @get:NotNull @get:Size(min = 5, max = 50) val rua: String,
        @get:NotNull @get:Size(min = 13, max = 13) val telefone: String,
        @get:NotNull val registradoPor: Usuario,
        val empresaConfig: EmpresaConfig
)

data class EmpresaConfig(
        @get:NotNull val qtyApartamentos: Long
)
