package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.*
import br.com.onsmarttech.thebutler.dtos.*
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.DocumentRepository
import br.com.onsmarttech.thebutler.repositories.FichaRepository
import br.com.onsmarttech.thebutler.util.S3Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
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

    @Autowired
    private lateinit var jasperReportsService: JasperReportsService

    fun save(dto: FichaDto): Ficha {
        val apartamento = apartamentoService.findById(dto.idApartamento)
        addRegistrador(dto.moradores)
        val moradoresSalvos = moradorService.saveAll(dto.moradores)
        return fichaRepository.save(Ficha(dto.id, apartamento, convertMoradoresToSub(moradoresSalvos), dto.dataInicio, null, null))
    }

    private fun addRegistrador(moradores: List<Morador>) {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        moradores.forEach { it.registradoPor = convertUsuarioToSub(usuarioLogado) }
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
        val ficha = getById(id)
        fichaRepository.deleteById(id)

        moradorService.removeMoradores(ficha.moradores!!.map { it.id })
    }

    fun getById(id: String) = fichaRepository.findById(id)
            .orElseThrow { BadRequestException("Ficha não encontrada") }!!

    fun getFullById(id: String): FichaFullResponseDto {
        val ficha = getById(id)
        val moradores = ficha.moradores
                ?.stream()
                ?.map { moradorService.findById(it.id!!) }
                ?.collect(Collectors.toList()) as List<Morador>

        return FichaFullResponseDto(ficha.id!!, ficha.apartamento, moradores, ficha.dataInicio, ficha.dataFim, ficha.documentos)
    }

    fun getAll(filter: FichaFilter, pageable: Pageable): Page<Ficha> {
        val userLogged = usuarioService.getUsuarioLogado()
        filter.idEmpresa = userLogged.empresa!!.id!!

        return fichaRepository.find(filter, pageable)
    }

    fun deleteDocumento(id: String, documentoId: String) {
        val ficha = fichaRepository.findByIdAndDocumentoId(id, documentoId)
                .orElseThrow { BadRequestException("Documento não encontrado") }

        ficha.documentos!!.removeIf { it.id == documentoId }
        fichaRepository.save(ficha)
    }

    fun uploadDocumento(fichaId: String, file: MultipartFile) {
        val userLogged = usuarioService.getUsuarioLogado()
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

    fun downloadPdf(id: String): ByteArrayInputStream {
        val fichaByteArray = jasperReportsService.fichaGenerate(getForJasper(id))
        return ByteArrayInputStream(fichaByteArray)
    }

    fun getForJasper(id: String): FichaJasperDto {
        val ficha = fichaRepository.findById(id)
                .orElseThrow { BadRequestException("Ficha não encontrada") }

        val moradores = moradorService.findInIds(ficha.moradores!!.map { it.id })
        val responsavel = moradores.firstOrNull { it.tipoMorador != null }

        return FichaJasperDto(
                convertApartamentoToApartamentoJasperDto(ficha.apartamento!!),
                convertMoradorToResponsavelJasperDto(responsavel!!),
                convertMoradorToMoradorJasperDto(moradores.filter { it.id != responsavel.id })
        )
    }

    fun removeMorador(id: String, moradorId: String) {
        val ficha = getById(id)
        moradorService.removeMoradores(listOf(moradorId))

        ficha.moradores!!.removeIf { it.id == moradorId }
        fichaRepository.save(ficha)
    }

}
