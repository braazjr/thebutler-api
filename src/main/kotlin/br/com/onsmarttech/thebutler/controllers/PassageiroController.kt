package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.documents.Passageiro
import br.com.onsmarttech.thebutler.dtos.PassageiroFilter
import br.com.onsmarttech.thebutler.services.PassageiroService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/api/passageiros")
class PassageiroController(val passageiroService: PassageiroService) {

    @GetMapping()
    fun findAll(pageable: Pageable, filter: PassageiroFilter) = ResponseEntity.ok(passageiroService.find(pageable, filter))

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = ResponseEntity.ok(passageiroService.findById(id))

    @PostMapping
    fun save(@RequestBody passageiro: Passageiro) = ResponseEntity.ok(passageiroService.save(passageiro))

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestBody passageiro: Passageiro) =
            ResponseEntity.ok(passageiroService.update(id, passageiro))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) = ResponseEntity.ok(passageiroService.delete(id))

    @PostMapping("/{id}/documento/upload-documento")
    fun uploadDocumento(@PathVariable("id") id: String, @Valid @NotNull @RequestParam("file") file: MultipartFile) =
            ResponseEntity.ok(passageiroService.uploadDocumento(id, file))

    @DeleteMapping("/{id}/documento/{documentoId}")
    fun deleteDocumento(@PathVariable("id") id: String, @PathVariable("documentoId") documentoId: String) =
            passageiroService.deleteDocumento(id, documentoId)
}