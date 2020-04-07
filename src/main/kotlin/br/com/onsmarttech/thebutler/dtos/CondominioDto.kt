package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Condominio
import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.documents.convertUsuarioToSub
import br.com.onsmarttech.thebutler.util.onlyAlphanumerics
import javax.validation.constraints.NotNull

data class CondominioDto(
        @get:NotNull val nome: String?,
        val ativo: Boolean = true,
        @get:NotNull val bairro: String?,
        @get:NotNull val cep: String?,
        @get:NotNull val cidade: String?,
        val complemento: String?,
        val email: String?,
        @get:NotNull val estado: String?,
        val numero: Int?,
        @get:NotNull val rua: String?,
        @get:NotNull val telefone: String?
)

fun convertDtoToCondominio(dto: CondominioDto, usuario: Usuario) = Condominio(null, dto.nome, dto.ativo, dto.bairro,
        onlyAlphanumerics(dto.cep!!),
        dto.cidade, dto.complemento, dto.email, dto.estado, dto.numero, dto.rua,
        onlyAlphanumerics(dto.telefone!!),
        usuario.empresa, convertUsuarioToSub(usuario))