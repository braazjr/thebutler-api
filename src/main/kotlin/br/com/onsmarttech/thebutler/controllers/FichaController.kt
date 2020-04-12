package br.com.onsmarttech.thebutler.controllers

import br.com.onsmarttech.thebutler.dtos.FichaDto
import br.com.onsmarttech.thebutler.services.FichaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/fichas")
class FichaController(val fichaService: FichaService) {

    @PostMapping
    fun save(@Valid @RequestBody ficha: FichaDto) = ResponseEntity.status(HttpStatus.CREATED).body(fichaService.save(ficha))

    @GetMapping("/apartamento/{id}")
    fun getByApartamentoId(@PathVariable("id") id: String) = ResponseEntity.ok(fichaService.getByApartamentoId(id))
}