package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.ApartamentoDto
import br.com.onsmarttech.thebutler.services.ApartamentoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/apartamentos")
class ApartamentoController(val apartamentoService: ApartamentoService) {

    @PostMapping
    fun salvar(@Valid @RequestBody apartamentoDto: ApartamentoDto) =
            ResponseEntity.status(HttpStatus.CREATED).body(apartamentoService.salvar(apartamentoDto))
}