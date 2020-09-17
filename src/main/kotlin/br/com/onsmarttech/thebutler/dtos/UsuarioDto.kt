package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.enums.Permissao
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UsuarioDto(
        val id: String?,
        @get:NotBlank val email: String?,
        @get:NotBlank val nome: String?,
        @JsonIgnore var senha: String?,
        val ativo: Boolean? = true,
        @get:NotEmpty val permissoes: List<Permissao>? = listOf(Permissao.OPERADOR),
        var empresaId: String?
)

fun convertDtoToUsuario(dto: UsuarioDto) = Usuario(dto.id, dto.email, dto.nome, dto.senha,
        dto.ativo, dto.permissoes, null)