package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Ficha
import br.com.onsmarttech.thebutler.documents.convertMoradoresToSub
import br.com.onsmarttech.thebutler.dtos.FichaDto
import br.com.onsmarttech.thebutler.repositories.FichaRepository
import br.com.onsmarttech.thebutler.repositories.MoradorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FichaService {

    @Autowired
    private lateinit var apartamentoService: ApartamentoService

    @Autowired
    private lateinit var fichaRepository: FichaRepository

    @Autowired
    private lateinit var moradorService: MoradorService

    fun save(dto: FichaDto): Ficha {
        val apartamento = apartamentoService.findById(dto.idApartamento)
        val moradoresSalvos = moradorService.saveAll(dto.moradores)
        return fichaRepository.save(Ficha(apartamento, convertMoradoresToSub(moradoresSalvos)))
    }

    fun getByApartamentoId(apartamentoId: String): List<Ficha> {
        apartamentoService.findById(apartamentoId)
        return fichaRepository.findByApartamentoId(apartamentoId)
    }

    fun getByMoradorId(moradorId: String): Any {
        moradorService.findById(moradorId)
        return fichaRepository.findByMoradorId(moradorId)
    }

}
