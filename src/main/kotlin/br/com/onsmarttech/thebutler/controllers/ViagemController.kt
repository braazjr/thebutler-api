package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.ViagemDto
import br.com.onsmarttech.thebutler.dtos.ViagemFilter
import br.com.onsmarttech.thebutler.services.ViagemService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/viagens")
class ViagemController(val viagemService: ViagemService) {

    @PostMapping
    fun save(@Valid @RequestBody viagemDto: ViagemDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(viagemService.save(viagemDto))

    @PostMapping("/multiple")
    fun saveMultiple(@Valid @RequestBody viagensDto: MutableList<ViagemDto>) =
            ResponseEntity.status(HttpStatus.CREATED).body(viagemService.saveMultiples(viagensDto))

    @GetMapping
    fun getAll(filter: ViagemFilter, pageable: Pageable) =
            ResponseEntity.ok(viagemService.getAll(filter, pageable))
}