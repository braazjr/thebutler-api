package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.enums.Permissao
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UsuarioDto(
        @get:NotBlank val email: String?,
        @get:NotBlank val nome: String?,
        @JsonIgnore var senha: String?,
        val ativo: Boolean? = true,
        @get:NotEmpty val permissoes: List<Permissao>? = listOf(Permissao.OPERADOR),
        var idEmpresa: String?
)

fun convertDtoToUsuario(dto: UsuarioDto) = Usuario(null, dto.email, dto.nome, dto.senha,
        dto.ativo, dto.permissoes, null)