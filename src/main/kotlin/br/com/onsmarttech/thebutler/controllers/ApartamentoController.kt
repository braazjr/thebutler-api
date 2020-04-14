package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.ApartamentoDto
import br.com.onsmarttech.thebutler.dtos.ApartamentoFilter
import br.com.onsmarttech.thebutler.services.ApartamentoService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/apartamentos")
class ApartamentoController(val apartamentoService: ApartamentoService) {

    @PostMapping
    fun save(@Valid @RequestBody apartamentoDto: ApartamentoDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(apartamentoService.save(apartamentoDto))

    @GetMapping
    fun list(filter: ApartamentoFilter, pageable: Pageable) = apartamentoService.list(filter, pageable)

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String) = ResponseEntity.ok(apartamentoService.findById(id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) = apartamentoService.delete(id)
}