package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.*
import br.com.onsmarttech.thebutler.dtos.MoradorDto
import br.com.onsmarttech.thebutler.dtos.MoradorFilter
import br.com.onsmarttech.thebutler.dtos.MoradorSimple
import br.com.onsmarttech.thebutler.dtos.convertDtoToMorador
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.DocumentRepository
import br.com.onsmarttech.thebutler.repositories.MoradorRepository
import br.com.onsmarttech.thebutler.util.S3Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class MoradorService {

    @Autowired
    private lateinit var moradorRepository: MoradorRepository

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var apartamentoService: ApartamentoService

    @Autowired
    private lateinit var s3Util: S3Util

    @Autowired
    private lateinit var documentRepository: DocumentRepository

    fun saveAll(apartamento: Apartamento, moradores: List<Morador>): MutableList<Morador> =
            moradores
                    .forEach {
                        if (it.id != null && it.id!!.isBlank()) {
                            it.id = null
                        }
                        it.apartamento = convertApartamentoToSub(apartamento)
//                        it.dataCriacao = LocalDate.now()
//                        it.dataAlteracao = LocalDate.now()

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

    fun save(morador: Morador): Morador {
        return moradorRepository.save(morador)
    }

    fun save(moradorDto: MoradorDto): Morador {
        return save(convertDtoToMorador(moradorDto, apartamentoService.findById(moradorDto.apartamentoId!!)))
    }

    fun update(id: String, moradorDto: MoradorDto): Morador {
        val morador = findById(id)
        moradorDto.id = id
        return save(moradorDto)
    }

    fun delete(id: String) {
        findById(id)
        moradorRepository.deleteById(id)
    }

    fun uploadDocumento(id: String, file: MultipartFile): String {
        val userLogged = usuarioService.getUsuarioLogado()
        val morador = findById(id)
        val uuid = UUID.randomUUID().toString()

        val url: String = s3Util.saveDocument("${morador.apartamento!!.bloco!!.condominio.empresa!!.id}/moradores/$id/documents/$uuid", file)
        val documentoSaved = documentRepository.save(Documento(null, uuid, url, file.originalFilename, convertUsuarioToSub(userLogged)))

        if (morador.documentos.isNullOrEmpty()) {
            morador.documentos = mutableListOf()
        }
        morador.documentos!!.add(documentoSaved)

        moradorRepository.save(morador)

        return url
    }

    fun deleteDocumento(id: String, documentoId: String) {
        val morador = moradorRepository.findByIdAndDocumentoId(id, documentoId)
                .orElseThrow { BadRequestException("Documento não encontrado") }

        morador.documentos!!.removeIf { it.id == documentoId }
        moradorRepository.save(morador)
    }

    fun findMoradoresSemFicha(empresaId: String): List<Morador> {
        return moradorRepository.findMoradoresSemFicha(empresaId)
    }

    fun setProprietario(id: String?) {
        val morador = findById(id!!)
        morador.tipoMorador = TipoMorador.PROPRIETARIO
        save(morador)
    }

}
