package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.*
import javax.validation.constraints.NotNull

data class ApartamentoDto(
        @get:NotNull val numero: String?,
        val ativo: Boolean = false,
        val numeroQuartos: Int?,
        @get:NotNull val idBloco: String?,
        val observacao: String?
)

fun convertDtoToApartamento(dto: ApartamentoDto, bloco: Bloco, usuario: Usuario) =
        Apartamento(null, dto.numero, dto.ativo, dto.numeroQuartos,
                BlocoSub(bloco.id, bloco.nome, bloco.numero),
                dto.observacao,
                convertUsuarioToSub(usuario)
        )