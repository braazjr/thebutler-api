package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.documents.convertApartamentoToSub
import br.com.onsmarttech.thebutler.dtos.MoradorFilter
import br.com.onsmarttech.thebutler.dtos.MoradorSimple
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.MoradorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MoradorService {

    @Autowired
    private lateinit var moradorRepository: MoradorRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun saveAll(apartamento: Apartamento, moradores: List<Morador>): MutableList<Morador> =
            moradores
                    .forEach {
                        if (it.id != null && it.id!!.isBlank()) {
                            it.id = null
                        }
                        it.apartamento = convertApartamentoToSub(apartamento)
                        it.dataCriacao = LocalDate.now()
                        it.dataAlteracao = LocalDate.now()

                        if (!it.id.isNullOrBlank()) {
                            val morador = findById(it.id!!)
                            it.dataCriacao = morador.dataCriacao
                        }
                    }
                    .run { return moradorRepository.saveAll(moradores) }

    fun findById(moradorId: String): Morador = moradorRepository.findById(moradorId)
            .orElseThrow { BadRequestException("Morador não encontrado") }

    fun findInIds(ids: List<String?>) = moradorRepository.findInIds(ids)

    fun removeMoradores(ids: List<String?>) = moradorRepository.deleteByIdIn(ids)

    fun find(pageable: Pageable, filter: MoradorFilter): Page<Morador> {
        val usuarioLogado = usuarioService.getUsuarioLogado()

        if (usuarioLogado.isAdmin()) {
            return moradorRepository.find(MoradorFilter(), pageable)
        }

        filter.empresaId = usuarioLogado.empresa!!.id
        return moradorRepository.find(filter, pageable)
    }

    fun simpleList(): List<MoradorSimple> {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        return moradorRepository.findSimpleByEmpresaId(usuarioLogado.empresa!!.id)
    }

    fun addFichaInMoradores(fichaId: String?, moradoresIds: List<String?>) {
        val moradores = moradorRepository.findInIds(moradoresIds)
        moradores.forEach { it.fichaId = fichaId!! }
        moradorRepository.saveAll(moradores)
    }

}
