package br.com.onsmarttech.thebutler.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Document
data class Empresa(

        @Id
        val id: String = "",
        val cnpj: @NotNull @Size(min = 18, max = 18) String,
        val nomeFantasia: @NotNull @Size(min = 3, max = 150) String,
        val razaoSocial: @NotNull @Size(min = 5, max = 150) String,
        val ativo: Boolean,
        val bairro: @NotNull @Size(min = 5, max = 30) String,
        val cep: @NotNull @Size(min = 9, max = 9) String,
        val cidade: @NotNull @Size(min = 5, max = 30) String,
        val complemento: String,
        val email: @Size(min = 5, max = 50) String,
        val estado: @NotNull @Size(min = 2, max = 2) String,
        val numero: Int,
        val rua: @NotNull @Size(min = 5, max = 50) String,
        val telefone: @NotNull @Size(min = 13, max = 13) String,
        val registradoPor: @NotNull Usuario,
        val empresaConfig: EmpresaConfig
)

data class EmpresaConfig(
        val qtyApartamentos: @NotNull Long
)
