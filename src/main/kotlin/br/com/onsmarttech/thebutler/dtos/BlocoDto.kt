package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BlocoDto(
        val id: String?,
        val ativo: Boolean = true,
        @get:NotNull val nome: String?,
        val numero: Int?,
        @get:NotBlank val idCondominio: String?
)

fun convertDtoToBloco(dto: BlocoDto, condominio: Condominio, usuario: Usuario) = Bloco(dto.id?: null, dto.ativo,
        dto.nome, dto.numero,
        CondominioSub(condominio.id, condominio.nome, condominio.email, condominio.empresa),
        convertUsuarioToSub(usuario))