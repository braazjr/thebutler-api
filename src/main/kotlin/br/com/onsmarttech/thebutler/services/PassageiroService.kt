package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.Documento
import br.com.onsmarttech.thebutler.documents.Passageiro
import br.com.onsmarttech.thebutler.documents.convertUsuarioToSub
import br.com.onsmarttech.thebutler.dtos.PassageiroFilter
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.DocumentRepository
import br.com.onsmarttech.thebutler.repositories.PassageiroRepository
import br.com.onsmarttech.thebutler.util.S3Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class PassageiroService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var passageiroRepository: PassageiroRepository

    @Autowired
    private lateinit var s3Util: S3Util

    @Autowired
    private lateinit var documentRepository: DocumentRepository

    fun find(pageable: Pageable, filter: PassageiroFilter): Page<Passageiro> {
        val usuarioLogado = usuarioService.getUsuarioLogado()

        if (usuarioLogado.isAdmin()) {
            return passageiroRepository.find(PassageiroFilter(), pageable)
        }

        filter.empresaId = usuarioLogado.empresa!!.id!!
        return passageiroRepository.find(filter, pageable)
    }

    fun findById(id: String): Passageiro {
        val passageiro = passageiroRepository.findById(id)
                .orElseThrow { BadRequestException("Passageiro não encontrado") }

        if (passageiro.responsavelId == null) {
            passageiro.dependentes = passageiroRepository.findByResponsavelId(passageiro.id!!).toMutableList()
        } else {
            return findById(passageiro.responsavelId)
        }

        return passageiro
    }

    fun save(passageiro: Passageiro): Passageiro {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        passageiro.empresa = usuarioLogado.empresa

        if (!passageiro.responsavelId.isNullOrBlank()) {
            val responsavel = findById(passageiro.responsavelId)
            passageiro.dependentes.add(responsavel!!.id!!)
        }

        return passageiroRepository.save(passageiro)
    }

    fun update(id: String, passageiro: Passageiro): Passageiro {
        findById(id)

        if (!passageiro.responsavelId.isNullOrBlank()) {
            val responsavel = findById(passageiro.responsavelId)

            if (responsavel!!.dependentes.count { it == passageiro.responsavelId } == 0) {
                passageiro.dependentes.add(responsavel!!.id!!)
            }
        }

        return save(passageiro)
    }

    fun delete(id: String) {
        findById(id)
        passageiroRepository.deleteById(id)
    }

    fun uploadDocumento(id: String, file: MultipartFile): Any {
        val userLogged = usuarioService.getUsuarioLogado()
        val morador = findById(id)
        val uuid = UUID.randomUUID().toString()

        val url: String = s3Util.saveDocument("${morador!!.empresa!!.id}/passageiros/$id/documents/$uuid", file)
        val documentoSaved = documentRepository.save(Documento(null, uuid, url, file.originalFilename, convertUsuarioToSub(userLogged)))

        if (morador.documentos.isNullOrEmpty()) {
            morador.documentos = mutableListOf()
        }
        morador.documentos!!.add(documentoSaved)

        passageiroRepository.save(morador)

        return url
    }

    fun deleteDocumento(id: String, documentoId: String) {
        val morador = passageiroRepository.findByIdAndDocumentoId(id, documentoId)
                .orElseThrow { BadRequestException("Documento não encontrado") }

        morador.documentos!!.removeIf { it.id == documentoId }
        passageiroRepository.save(morador)
    }
}
