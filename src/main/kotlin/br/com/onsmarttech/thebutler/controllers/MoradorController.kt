package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.MoradorFilter
import br.com.onsmarttech.thebutler.services.MoradorService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/moradores")
class MoradorController(val moradorService: MoradorService) {

    @GetMapping("/simple")
    fun simpleList() = ResponseEntity.ok(moradorService.simpleList())

    @GetMapping()
    fun findAll(pageable: Pageable, filter: MoradorFilter) = ResponseEntity.ok(moradorService.find(pageable, filter))
}