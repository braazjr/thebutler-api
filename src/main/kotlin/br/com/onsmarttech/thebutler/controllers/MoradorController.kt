package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.MoradorDto
import br.com.onsmarttech.thebutler.dtos.MoradorFilter
import br.com.onsmarttech.thebutler.services.MoradorService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/moradores")
class MoradorController(val moradorService: MoradorService) {

    @GetMapping("/simple")
    fun simpleList() = ResponseEntity.ok(moradorService.simpleList())

    @GetMapping()
    fun findAll(pageable: Pageable, filter: MoradorFilter) = ResponseEntity.ok(moradorService.find(pageable, filter))

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = ResponseEntity.ok(moradorService.findById(id))

    @PostMapping
    fun save(@RequestBody morador: MoradorDto) = ResponseEntity.ok(moradorService.save(morador))

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody morador: MoradorDto) =
            ResponseEntity.ok(moradorService.update(id, morador))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) = ResponseEntity.ok(moradorService.delete(id))

    @PostMapping("/{id}/documento/upload-documento")
    fun uploadDocumento(@PathVariable("id") id: String, @Valid @NotNull @RequestParam("file") file: MultipartFile) =
            ResponseEntity.ok(moradorService.uploadDocumento(id, file))

    @DeleteMapping("/{id}/documento/{documentoId}")
    fun deleteDocumento(@PathVariable("id") id: String, @PathVariable("documentoId") documentoId: String) =
            moradorService.deleteDocumento(id, documentoId)
}