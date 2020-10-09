package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.documents.Morador
import br.com.onsmarttech.thebutler.dtos.FichaDto
import br.com.onsmarttech.thebutler.dtos.FichaFilter
import br.com.onsmarttech.thebutler.services.FichaService
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/fichas")
class FichaController(val fichaService: FichaService) {

    @PostMapping
    fun save(@Valid @RequestBody ficha: FichaDto) = ResponseEntity.status(HttpStatus.CREATED).body(fichaService.save(ficha))

    @GetMapping("/apartamento/{id}")
    fun getByApartamentoId(@PathVariable("id") id: String) = ResponseEntity.ok(fichaService.getByApartamentoId(id))

    @GetMapping("/morador/{id}")
    fun getByMoradorId(@PathVariable("id") id: String) = ResponseEntity.ok(fichaService.getByMoradorId(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) = fichaService.delete(id)

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = fichaService.getById(id)

    @GetMapping("/{id}/full")
    fun getFullById(@PathVariable("id") id: String) = fichaService.getFullById(id)

    @GetMapping
    fun getAll(filter: FichaFilter, pageable: Pageable) = fichaService.getAll(filter, pageable)

    @DeleteMapping("/{id}/documento/{documentoId}")
    fun deleteDocumento(@PathVariable("id") id: String, @PathVariable("documentoId") documentoId: String) =
            fichaService.deleteDocumento(id, documentoId)

    @PostMapping("/{id}/documento/upload-documento")
    fun uploadDocumento(@PathVariable("id") id: String, @Valid @NotNull @RequestParam("file") file: MultipartFile) =
            ResponseEntity.ok(fichaService.uploadDocumento(id, file))

    @GetMapping("/{id}/download-pdf")
    fun downloadPdf(@PathVariable("id") id: String, response: HttpServletResponse) {
        val pdf = fichaService.downloadPdf(id)
        response.addHeader("filename", "ficha_${id}_thebutler.pdf")

        IOUtils.copy(pdf, response.outputStream)
        response.flushBuffer()
    }

    @GetMapping("/{id}/for-jasper")
    fun getForJasper(@PathVariable("id") id: String) = fichaService.getForJasper(id)

    @PatchMapping("/{id}/remove-morador/{moradorId}")
    fun removeMorador(@PathVariable("id") id: String, @PathVariable("moradorId") moradorId: String) =
            fichaService.removeMorador(id, moradorId)

    @GetMapping("/{id}/documentos")
    fun getDocumentosByFichaId(@PathVariable("id") fichaId: String) =
            ResponseEntity.ok(fichaService.getDocumentosByFichaId(fichaId))

    @GetMapping("/morador/{id}/full")
    fun getFullByMoradorId(@PathVariable("id") id: String) = ResponseEntity.ok(fichaService.getFullByMoradorId(id))

    @PatchMapping("/{id}/add-morador")
    fun addMoradorOnFicha(@PathVariable("id") fichaId: String, @RequestBody morador: Morador) =
            ResponseEntity.ok(fichaService.addMorador(fichaId, morador))
}