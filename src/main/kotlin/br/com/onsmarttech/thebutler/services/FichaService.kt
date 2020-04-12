package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Ficha
import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.documents.convertMoradoresToSub
import br.com.onsmarttech.thebutler.dtos.FichaDto
import br.com.onsmarttech.thebutler.dtos.FichaFullResponse
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.FichaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class FichaService {

    @Autowired
    private lateinit var apartamentoService: ApartamentoService

    @Autowired
    private lateinit var fichaRepository: FichaRepository

    @Autowired
    private lateinit var moradorService: MoradorService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun save(dto: FichaDto): Ficha {
        val apartamento = apartamentoService.findById(dto.idApartamento)
        val moradoresSalvos = moradorService.saveAll(dto.moradores)
        return fichaRepository.save(Ficha(null, apartamento, convertMoradoresToSub(moradoresSalvos), dto.dataInicio, null, null))
    }

    fun getByApartamentoId(apartamentoId: String): List<Ficha> {
        apartamentoService.findById(apartamentoId)
        return fichaRepository.findByApartamentoId(apartamentoId)
    }

    fun getByMoradorId(moradorId: String): Any {
        moradorService.findById(moradorId)
        return fichaRepository.findByMoradorId(moradorId)
    }

    fun delete(id: String) {
        getById(id)
        fichaRepository.deleteById(id)
    }

    fun getById(id: String) = fichaRepository.findById(id)
            .orElseThrow { BadRequestException("Ficha não encontrada") }

    fun getFullById(id: String): FichaFullResponse {
        val ficha = getById(id)
        val moradores = ficha.moradores
                ?.stream()
                ?.map { moradorService.findById(it.id!!) }
                ?.collect(Collectors.toList()) as List<Morador>

        return FichaFullResponse(ficha.id!!, ficha.apartamento, moradores, ficha.dataInicio, ficha.dataFim)
    }

    fun getAll(pageable: Pageable): Page<Ficha> {
        val userLogged = usuarioService.getUsuario()

        return fichaRepository.findByEmpresa(userLogged.empresa!!.id, pageable)
    }

    fun deleteDocumento(documentoId: String) {
        val ficha = fichaRepository.findByDocumentoId(documentoId)
        ficha.documentos = ficha.documentos!!.stream()
                .filter { it.id != documentoId }
                .collect(Collectors.toList())

        fichaRepository.save(ficha)
    }

}
