package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.FichaDto
import br.com.onsmarttech.thebutler.services.FichaService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    fun getAll(pageable: Pageable) = fichaService.getAll(pageable)

    @DeleteMapping("/documento/{documentoId}")
    fun deleteDocumento(@PathVariable("documentoId") documentoId: String) = fichaService.deleteDocumento(documentoId)

    @PostMapping("/{id}/documento/upload-documento")
    fun uploadDocumento(@PathVariable("id") id: String, @Valid @NotNull @RequestParam("file") file: MultipartFile) =
            fichaService.uploadDocumento(id, file)
}