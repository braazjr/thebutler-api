package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BlocoDto(

        val ativo: Boolean = true,
        @get:NotNull val nome: String?,
        val numero: Int?,
        @get:NotBlank val idCondominio: String?
)

fun convertDtoToBloco(dto: BlocoDto, condominio: Condominio, usuario: Usuario) = Bloco(null, dto.ativo, dto.nome,
        dto.numero,
        CondominioSub(condominio.id, condominio.nome, condominio.email, condominio.empresa),
        convertUsuarioToSub(usuario))