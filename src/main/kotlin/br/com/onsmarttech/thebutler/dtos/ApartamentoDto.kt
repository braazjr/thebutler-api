package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.*
import javax.validation.constraints.NotNull

data class ApartamentoDto(
        val id: String?,
        @get:NotNull val numero: String?,
        val ativo: Boolean = false,
        val numeroQuartos: Int?,
        @get:NotNull val idBloco: String?,
        val observacao: String?
)

fun convertDtoToApartamento(dto: ApartamentoDto, bloco: Bloco, usuario: Usuario) =
        Apartamento(dto.id, dto.numero, dto.ativo, dto.numeroQuartos,
                BlocoSub(bloco.id, bloco.nome, bloco.numero,
                        CondominioSub(bloco.condominio!!.id, bloco.condominio.nome, bloco.condominio.email,
                                bloco.condominio.empresa)),
                dto.observacao,
                convertUsuarioToSub(usuario)
        )