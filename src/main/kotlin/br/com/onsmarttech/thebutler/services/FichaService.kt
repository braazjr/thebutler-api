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
    private lateinit var moradorRepository: MoradorRepository

    //    fun getByApartamentoId(apartamentoId: String): Any {
//        val apartamento = apartamentoService.findById(apartamentoId)
//
//        return
//    }

    fun save(dto: FichaDto): Ficha {
        val apartamento = apartamentoService.findById(dto.idApartamento)
        val moradoresSalvos = moradorRepository.saveAll(dto.moradores)
        return fichaRepository.save(Ficha(apartamento, convertMoradoresToSub(moradoresSalvos)))
    }

}
