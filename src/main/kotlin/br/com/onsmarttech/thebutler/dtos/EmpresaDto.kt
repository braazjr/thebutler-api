package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Empresa
import br.com.onsmarttech.thebutler.documents.EmpresaConfig
import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.documents.convertUsuarioToSub
import br.com.onsmarttech.thebutler.util.onlyAlphanumerics
import javax.validation.constraints.NotNull

data class EmpresaDto(
        val id: String?,
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
//        @get:NotNull val qtyApartamentos: Int,
        val empresaConfig: EmpresaConfig?
)

fun convertDtoToEmpresa(dto: EmpresaDto, usuario: Usuario): Empresa {
    return Empresa(dto.id?: null,
            onlyAlphanumerics(dto.cnpj!!),
            dto.nomeFantasia, dto.razaoSocial, dto.ativo, dto.bairro,
            onlyAlphanumerics(dto.cep!!),
            dto.cidade,
            dto.complemento, dto.email, dto.estado, dto.numero, dto.rua,
            onlyAlphanumerics(dto.telefone!!),
            convertUsuarioToSub(usuario),
            dto.empresaConfig)
}