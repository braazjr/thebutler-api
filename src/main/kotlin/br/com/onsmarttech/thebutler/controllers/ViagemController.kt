package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.ViagemDto
import br.com.onsmarttech.thebutler.services.ViagemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/viagens")
class ViagemController(val viagemService: ViagemService) {

    @PostMapping
    fun save(@Valid @RequestBody viagemDto: ViagemDto) = ResponseEntity.status(HttpStatus.CREATED).body(viagemService.save(viagemDto))
}