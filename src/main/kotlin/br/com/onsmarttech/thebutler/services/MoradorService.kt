package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Apartamento
import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.documents.convertApartamentoToSub
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.MoradorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MoradorService {

    @Autowired
    private lateinit var moradorRepository: MoradorRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun saveAll(apartamento: Apartamento, moradores: List<Morador>): MutableList<Morador> {
        moradores.forEach {
            it.apartamento = convertApartamentoToSub(apartamento)
            it.dataCriacao = LocalDate.now()
            it.dataAlteracao = LocalDate.now()

            if (!it.id.isNullOrBlank()) {
                val morador = findById(it.id)
                it.dataCriacao = morador.dataCriacao
            }
        }

        return moradorRepository.saveAll(moradores)
    }

    fun findById(moradorId: String) = moradorRepository.findById(moradorId)
            .orElseThrow { BadRequestException("Morador não encontrado") }

    fun findInIds(ids: List<String?>) = moradorRepository.findInIds(ids)

    fun removeMoradores(ids: List<String?>) = moradorRepository.deleteByIdIn(ids)

    fun find(): List<Morador> {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        return moradorRepository.findAll(usuarioLogado.empresa!!.id)
    }

}
