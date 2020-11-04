package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.*
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class MoradorDto(
        var id: String?,
        @get:NotNull
        val documento: String?,
        @get:NotBlank
        val celular: String?,
        @get:NotBlank
        val nome: String?,
        @get:NotNull
        val tipoDocumento: TipoDocumento?,
        val ativo: Boolean?,
        val email: String?,
        val telefone: String?,
        val observacao: String?,
        val tipoMorador: TipoMorador?,
        val foto64: String?,
        var apartamentoId: String?,
        val dataInicio: LocalDate?,
        val dataFim: LocalDate?
)

fun convertDtoToMorador(dto: MoradorDto, apartamento: Apartamento): Morador {
    val morador = Morador(dto.id, dto.documento, dto.celular, dto.nome, dto.tipoDocumento)
    morador.ativo = dto.ativo!!
    morador.email = dto.email!!
    morador.telefone = dto.telefone!!
    morador.observacao = dto.observacao!!
    morador.tipoMorador = dto.tipoMorador
    morador.foto64 = dto.foto64
    morador.apartamento = convertApartamentoToSub(apartamento)
    morador.dataInicio = dto.dataInicio
    morador.dataFim = dto.dataFim

    return morador
}