package br.com.onsmarttech.thebutler.services

import br.com.onsmarttech.thebutler.documents.*
import br.com.onsmarttech.thebutler.dtos.*
import br.com.onsmarttech.thebutler.exception.BadRequestException
import br.com.onsmarttech.thebutler.repositories.DocumentRepository
import br.com.onsmarttech.thebutler.repositories.FichaRepository
import br.com.onsmarttech.thebutler.util.S3Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.time.LocalDate
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
        if (!dto.id.isNullOrBlank()) {
            var ficha = getById(dto.id)
            ficha = fichaRepository.save(fillFicha(dto, ficha.dataCriacao!!, LocalDate.now()))
            addFichaInMoradores(ficha.id, ficha.moradores)
            return ficha
        }

        val ficha = fichaRepository.save(fillFicha(dto, LocalDate.now(), LocalDate.now()))
        addFichaInMoradores(ficha.id, ficha.moradores)
        return ficha
    }

    private fun addFichaInMoradores(fichaId: String?, moradores: MutableList<MoradorSub>?) {
        moradorService.addFichaInMoradores(fichaId, moradores!!.map { it.id })
    }

    private fun addRegistrador(moradores: List<Morador>) {
        val usuarioLogado = usuarioService.getUsuarioLogado()
        moradores.forEach { it.registradoPor = convertUsuarioToSub(usuarioLogado) }
    }

    private fun fillFicha(dto: FichaDto, dataCriacao: LocalDate, dataAlteracao: LocalDate): Ficha {
        val apartamento = apartamentoService.findById(dto.idApartamento)
        addRegistrador(dto.moradores)
        val moradoresSalvos = moradorService.saveAll(apartamento, dto.moradores)
        return Ficha(
            dto.id, apartamento, convertMoradoresToSub(moradoresSalvos), dto.dataInicio, dto.dataFim,
            null, dataCriacao, dataAlteracao
        )
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

        return FichaFullResponseDto(
            ficha.id!!,
            ficha.apartamento,
            moradores,
            ficha.dataInicio,
            ficha.dataFim,
            ficha.documentos
        )
    }

    fun getAll(filter: FichaFilter, pageable: Pageable): Page<Ficha> {
        val userLogged = usuarioService.getUsuarioLogado()
        filter.empresaId = if (userLogged.isAdmin()) null else userLogged.empresa!!.id!!

        return fichaRepository.find(filter, pageable)
    }

    fun deleteDocumento(id: String, documentoId: String) {
        val ficha = fichaRepository.findByIdAndDocumentoId(id, documentoId)
            .orElseThrow { BadRequestException("Documento não encontrado") }

        ficha.documentos!!.removeIf { it.id == documentoId }
        fichaRepository.save(ficha)
    }

    fun uploadDocumento(fichaId: String, file: MultipartFile): String {
        val userLogged = usuarioService.getUsuarioLogado()
        val ficha = fichaRepository.findById(fichaId)
            .orElseThrow { BadRequestException("Ficha não encontrada") }

        val uuid = UUID.randomUUID().toString()

        val url: String = s3Util.saveDocument(
            "${ficha.apartamento!!.bloco!!.condominio.empresa!!.id}/fichas/$fichaId/documents/$uuid",
            file
        )

        val documentoSaved =
            documentoRepository.save(Documento(null, uuid, url, file.originalFilename, convertUsuarioToSub(userLogged)))

        if (ficha.documentos.isNullOrEmpty()) {
            ficha.documentos = mutableListOf()
        }
        ficha.documentos!!.add(documentoSaved)

        fichaRepository.save(ficha)

        return url
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

    fun getDocumentosByFichaId(fichaId: String) = getById(fichaId).documentos!!.distinct()

    fun getFullByMoradorId(moradorId: String): List<FichaFullResponseDto> {
        moradorService.findById(moradorId)
        return fichaRepository.findByMoradorId(moradorId)
            .map { it: Ficha ->
                val moradores = it.moradores
                    ?.map { morador -> moradorService.findById(morador.id!!) }

                FichaFullResponseDto(it.id!!, it.apartamento, moradores, it.dataInicio, it.dataFim, it.documentos)
            }
    }

    fun addMorador(fichaId: String, moradorDto: Morador): Morador {
        val ficha = getById(fichaId)
        var morador = moradorDto

        if (morador.id.isNullOrBlank()) {
            val usuarioLogado = usuarioService.getUsuarioLogado()
            morador.registradoPor = convertUsuarioToSub(usuarioLogado)
            morador.apartamento = convertApartamentoToSub(apartamentoService.findById(ficha.apartamento!!.id!!))
            morador.fichaId = ficha.id!!
            morador = moradorService.save(morador)
        } else {
            morador = moradorService.save(morador)
            ficha.moradores!!.removeIf { it.id == morador.id }
        }

        ficha.moradores!!.add(convertMoradorToSub(morador))
        fichaRepository.save(ficha)

        return morador
    }

    fun processaFichaPorApartamentos(empresaId: String) {
        val moradoresSemFicha = moradorService.findMoradoresSemFicha(empresaId)
        val fichas = mutableListOf<Ficha>()

        moradoresSemFicha.forEach { morador ->
            val ficha = fichas.find { ficha -> ficha.apartamento!!.id == morador.apartamento!!.id }
            if (ficha == null) {
                fichas.add(
                    Ficha(
                        apartamentoService.findById(morador.apartamento!!.id!!),
                        mutableListOf(convertMoradorToSub(morador))
                    )
                )

                moradorService.setProprietario(morador.id)
            } else {
                ficha.moradores!!.add(convertMoradorToSub(morador))
            }
        }

        val fichasSalvas = fichaRepository.saveAll(fichas)
        fichasSalvas.forEach { addFichaInMoradores(it.id, it.moradores) }
    }
}

