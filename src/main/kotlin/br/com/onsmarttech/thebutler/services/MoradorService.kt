package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.MoradorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MoradorService {

    @Autowired
    private lateinit var moradorRepository: MoradorRepository

    fun saveAll(moradores: List<Morador>) = moradorRepository.saveAll(moradores)

    fun findById(moradorId: String) = moradorRepository.findById(moradorId)
            .orElseThrow { BadRequestException("Morador não encontrado") }

    fun findInIds(ids: List<String?>) = moradorRepository.findInIds(ids)

}
