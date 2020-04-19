package br.com.onsmarttech.thebutler.dtos

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.documents.Morador

data class FichaJasperDto (
        val apartamento: ApartamentoJasperDto,
        val responsavel: ResponsavelJasperDto,
        val moradores: List<MoradorJasperDto>
)

data class ApartamentoJasperDto(
        val nomeCondominio: String,
        val nomeBloco: String,
        val numeroApartamento: String,
        val numeroQuartosSociais: Int
)

data class ResponsavelJasperDto(
        val nome: String,
        val codigo: String,
        val telefone: String,
        val celular: String,
        val documento: String,
        val email: String,
        val placaCarro: String,
        val observacao: String
)

data class MoradorJasperDto(
        val nome: String,
        val parentesco: String,
        //        val dataCadastro: String,
        val email: String,
        val telefone: String,
        val documento: String,
        val codigo: String,
        val placaCarro: String
)

fun convertApartamentoToApartamentoJasperDto(apartamento: Apartamento) =
        ApartamentoJasperDto(apartamento.bloco!!.condominio.nome!!, apartamento.bloco!!.nome!!, apartamento.numero!!,
                apartamento.numeroQuartos ?: 0)

fun convertMoradorToResponsavelJasperDto(morador: Morador) =
        ResponsavelJasperDto(morador.nome!!, morador.id!!, morador.telefone ?: "", morador.celular ?: "",
                morador.documento ?: "", morador.email ?: "", morador.placaCarro ?: "",
                morador.observacao ?: "")

fun convertMoradorToMoradorJasperDto(moradores: List<Morador>) =
        moradores.map {
            MoradorJasperDto(it.nome!!, it.parentesco ?: "", it.email ?: "", it.telefone ?: "",
                    it.documento ?: "", it.id!!, it.placaCarro ?: "")
        }