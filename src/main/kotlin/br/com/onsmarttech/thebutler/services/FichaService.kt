package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.*
import br.com.onsmarttech.thebutler.dtos.FichaDto
import br.com.onsmarttech.thebutler.dtos.FichaFullResponse
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.DocumentRepository
import br.com.onsmarttech.thebutler.repositories.FichaRepository
import br.com.onsmarttech.thebutler.util.S3Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
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

    @Autowired
    private lateinit var documentoRepository: DocumentRepository

    @Autowired
    private lateinit var s3Util: S3Util

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

    fun deleteDocumento(id: String, documentoId: String) {
        val ficha = fichaRepository.findByIdAndDocumentoId(id, documentoId)
                .orElseThrow { BadRequestException("Documento não encontrado") }

        ficha.documentos!!.removeIf { it.id == documentoId }
        fichaRepository.save(ficha)
    }

    fun uploadDocumento(fichaId: String, file: MultipartFile) {
        val userLogged = usuarioService.getUsuario()
        val fichaOptional = fichaRepository.findById(fichaId)

        if (fichaOptional.isPresent) {
            val uuid = UUID.randomUUID().toString()
            val ficha = fichaOptional.get()

            val url: String = s3Util.saveDocument("${fichaOptional.get().apartamento!!.bloco!!.condominio.empresa!!.id}/fichas/$fichaId/documents/$uuid", file)

            val documentoSaved = documentoRepository.save(Documento(null, uuid, url, convertUsuarioToSub(userLogged)))

            if (ficha.documentos.isNullOrEmpty()) {
                ficha.documentos = mutableListOf()
            }
            ficha.documentos!!.add(documentoSaved)

            fichaRepository.save(ficha)
        }
    }

}
