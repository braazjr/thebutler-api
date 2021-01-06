package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Usuario
import br.com.onsmarttech.thebutler.enums.Permissao
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class UsuarioDto(
        var id: String?,
        @get:NotBlank val username: String?,
        @get:NotBlank val nome: String?,
        @JsonIgnore var senha: String?,
        val ativo: Boolean? = true,
        @get:NotEmpty val permissoes: MutableList<Permissao>? = mutableListOf(Permissao.OPERADOR),
        var empresaId: String?,
        val foto64: String?
)

fun convertDtoToUsuario(dto: UsuarioDto) = Usuario(dto.id, dto.username, dto.nome, dto.senha,
        dto.ativo, dto.permissoes as MutableList<Any>, null, dto.foto64)